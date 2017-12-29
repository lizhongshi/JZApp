package com.dly.app.commons.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.commons.security.AES;
import com.dly.app.commons.security.SHA1;
/**
 * 前置过滤器
 * @author lzs
 *
 */
public class PerFilter implements Filter{
	private static int length=50;
	private static String key="";
	private static Logger log = Logger.getLogger(PerFilter.class);
	RedisCacheUtil redisUtil;
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("filter");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
	HttpServletRequest httpreq=(HttpServletRequest)req;
	HttpServletResponse httpres=(HttpServletResponse)res;
	
	
	log.info(httpreq.getRequestURL());
	//System.out.println(httpreq.getHeader(""));
	log.info(httpreq.getMethod());
	log.info(httpreq.getContentType());
	//System.out.println(httpreq.getRemoteAddr());
	JSONObject reqjson=null;
	log.info("HTTPContentType------->"+httpreq.getContentType());
	if(("POST".equals(httpreq.getMethod())&&"application/json;charset=UTF-8".equals(httpreq.getContentType()))||"DELETE".equals(httpreq.getMethod())&&"application/json;charset=UTF-8".equals(httpreq.getContentType())) {//访问接口时验证数据是否被篡改
		MyHttpServletRequestWrapper beforeReq=new MyHttpServletRequestWrapper(httpreq);//前置req
		MyHttpServletRequestWrapper afterReq=null;
		BufferedReader br=beforeReq.getReader();
		String str, wholeStr = "";
		while((str = br.readLine()) != null){
		wholeStr += str;
		}
		log.info("源数据----->"+wholeStr);
		JSONObject json=null;
		try {
			json=JSONObject.parseObject(wholeStr);	
		}catch(JSONException e) {
			httpres.sendError(401, "参数格式错误");
			return;
		}
		try {
		String decrypted=	AES.aesDecrypt(json.getString("AesData"),AES.KEY);//解密
		log.info("AesData::"+decrypted);
		reqjson=JSONObject.parseObject(decrypted);
		afterReq=new MyHttpServletRequestWrapper(httpreq,reqjson.toJSONString().getBytes());//重新组装参数
		log.info("请求参数密码"+decrypted);
		
		} catch (Exception e) {
			httpres.sendError(403, "数据解析失败,拒绝服务---403-1");
			e.printStackTrace();
			return;
		}
		/*
		 * 验证接口时间戳是否合法 30秒
		 */
		//System.out.println("请求参数"+json);
		String[] data=new String[length];
		String sign="";
		try {
				Long reqTime=Long.valueOf(reqjson.getString("timeStamp"));
				Date date=new Date();
//				System.out.println("参数时间戳"+reqTime);
//				System.out.println("服务器时间戳"+date.getTime());
				if(date.getTime()-reqTime>30000&&date.getTime()-reqTime<30000) {
					httpres.sendError(403, "认证失败,拒绝服务---403-2");
					return ;
				}else {//时间戳通过验证签名是否正确
					 sign=json.getString("sign");//前台传过来的签名
//					 log.info("数据:::"+reqjson);
//					 log.info("签名:::"+sign);
				Map map=(Map) JSON.parse(reqjson.toJSONString());//前台传过来的数据
					int i=1;
					data[0]=key;
					for (Object string : map.keySet()) {
						//System.out.println(i+"====="+string+map.get(string));
							data[i]=string+(String) map.get(string);
							i++;
					}
//					for (int j = 0; j < data.length; j++) {
//						System.out.println("-------"+data[j]);
//					}
				}
			}catch(ClassCastException cce) {
				cce.printStackTrace();
			}catch(JSONException je) {
				je.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(!SHA1.verifySign(data,sign)) {
				httpres.sendError(403, "数据解密失败---403-3");	
				return;
			}
			//对非注册登录接口进行令牌验证
			String reqUrl=httpreq.getRequestURI().substring(httpreq.getRequestURI().lastIndexOf("/")+1, httpreq.getRequestURI().length());
			log.info(reqUrl);
			if(!reqUrl.equals("login")&&!reqUrl.equals("register")) {
				log.info("验证令牌"+reqjson.toJSONString());
				String tokenId=reqjson.getString("tokenId");//获取用户令牌
				String userId=null;
				
					userId=redisUtil.getValue(tokenId);
			
				log.info("tokenId:"+tokenId+"userid:"+userId);
					if(userId!=null){//如果userid不为空,说明这个token有效,为这个token续命
						redisUtil.upKey(tokenId);
						chain.doFilter(afterReq, res);
				}else {
					httpres.sendError(401, "会话失效请重新登录---401-4");
					return;
				}
			//postReq.setAttribute("isyz", "1");
		}else {
			//不是登录注册接口
			chain.doFilter(afterReq, res);
			return;
		}
	}else {
		
		chain.doFilter(httpreq, res);
	}
	  
	}

	public void init(FilterConfig arg0) throws ServletException {
		  ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		redisUtil=ctx.getBean(RedisCacheUtil.class);
		log.info("filter init");
	}

}
