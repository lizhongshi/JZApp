package com.dly.app.service.imple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dly.app.commons.Util;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.commons.sms.ShortMessage;
import com.dly.app.commons.sms.ShortMessageBean;
import com.dly.app.commons.sms.SmsBean;
import com.dly.app.service.SmsService;
@Service("smsService")
public class SmsServiceImple implements SmsService {
    @Resource
    public  SmsBean smsBean;
    @Resource
	public RedisCacheUtil redisUtil;
    @Resource
   	public ShortMessage shortMessage;
	public Result  sendSms(String phoneNmb) { 
		if(phoneNmb==null) {
			 return new Result("false", "99","短信发送失败", "手机号不能为空");
		}
		Map<String,String> map= shortMessage.sendSms(phoneNmb);
		String message=map.get("info");
		redisUtil.cacheValue(phoneNmb, message, 300);
		
		if(map.get("code").equals("OK")) {
			return new Result("true", "0","短信发送成功", "");
		}else {
			 return new Result("false", "99","短信发送失败", map.get("message"));
		}
    

	}
	  public  QuerySendDetailsResponse querySendDetails(String bizId) {

	        //可自助调整超时时间
	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsBean.getAccessKeyId(),  smsBean.getAccessKeySecret());
	        try {
				DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou",  smsBean.getProduct(),  smsBean.getDomain());
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        IAcsClient acsClient = new DefaultAcsClient(profile);

	        //组装请求对象
	        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
	        //必填-号码
	        request.setPhoneNumber("15000000000");
	        //可选-流水号
	        request.setBizId(bizId);
	        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
	        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	        request.setSendDate(ft.format(new Date()));
	        //必填-页大小
	        request.setPageSize(10L);
	        //必填-当前页码从1开始计数
	        request.setCurrentPage(1L);

	        //hint 此处可能会抛出异常，注意catch
	        QuerySendDetailsResponse querySendDetailsResponse=null;
			try {
				querySendDetailsResponse = acsClient.getAcsResponse(request);
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        return querySendDetailsResponse;
	    }

}
