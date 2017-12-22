package com.dly.app.commons;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.csource.common.MyException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.fastdfs.FastdfsUtil;
import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.commons.util.ImageUtil;
import com.dly.app.dao.MediaDAO;
import com.dly.app.pojo.Image;


@Component
public class Util {
	@Resource
	public MediaDAO mediaDao;
	
	public static synchronized String getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	public static  String Md5(String pwd,String salt) {
		
		return DigestUtils.md5Hex(pwd+salt);
	}

	  
	public static void main(String[] args) {
		
		
		String id=getUUID();
		//System.out.println(id);
		
		String idd=Md5("123456","3749faf3b6954babaa2322124525c80b");
		System.out.println(idd);
		
	
	}
	
	public static Object poststr(Object o) {
//		JSONObject json=JSONObject.;
//		try {
//			json
//		}catch(Exception e) {
//			
//		}
		
		return null;
	}
	/**
	 * 生成验证码并放进缓存
	 * @param response
	 * @param key 用户id
	 * @return 验证码
	 */
	public  String verificationCode(HttpServletResponse response,String key) {
		
		//利用图片工具生成图片  
	    //第一个参数是生成的验证码，第二个参数是生成的图片  
	    Object[] objs = ImageUtil.createImage();  
	    //将验证码存入Session  
	    //session.setAttribute("imageCode",objs[0]);  
	    System.out.println(objs[0]);
	    String val=String.valueOf(objs[0]);
	    //将图片输出给浏览器  
	    BufferedImage image = (BufferedImage) objs[1];  
	    response.setContentType("image/png");  
	    OutputStream os;
		try {
			os = response.getOutputStream();
			ImageIO.write(image, "png", os);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	   RedisCacheUtil redisUtil=new RedisCacheUtil();
	    redisUtil.cacheValue(key, val.toString(), 300);
	    return val;
	}
	/**
	 * map转成json字符串
	 * @param map
	 * @return
	 */
	public static JSONObject mapToJsonObj(Map<String, String> map) {
		JSONObject json=new JSONObject();
		for (Object key : map.keySet()) {
			json.put(key.toString(), map.get(key));
		}
		return json;
	}
	public static String randomNumber() {
	Random r=new Random();
	StringBuffer randomNumber=new StringBuffer();
	for (int i = 0; i < 4; i++) {
		randomNumber.append(r.nextInt(10));
	}
	return  randomNumber.toString();
		
	}
	public  void showDirectory(File file,String title){
        File[] files = file.listFiles();
        
        

        for(File a:files){
        	
        	if(!a.isDirectory()) {//不是文件夹
        		   System.out.println(a.getAbsolutePath());
        		   System.out.println("title"+title);
        		   try {
				String s[]=	FastdfsUtil.upLoad(a);
				 BufferedImage sourceImg =ImageIO.read(new FileInputStream(a)); 
				Image image=new Image();
				image.setImageUrl("/"+s[0]+"/"+s[1]);
				image.setGroup(s[0]);
				image.setTitle(title);
				image.setImageName(a.getName());
				image.setHeight(String.valueOf(sourceImg.getHeight()));
				image.setWidth(String.valueOf(sourceImg.getWidth()));
				image.setType("0");
				mediaDao.addImage(image);
				} catch (MyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		   System.out.println(a.getName().substring(a.getName().lastIndexOf(".")+1, a.getName().length()));
        	}
         
            
            if(a.isDirectory()){//是文件夹
            	System.out.println(a.getName());
            	title=a.getName();
                showDirectory(a,title);
            }
        }
    }
	
	/** 
     * MultipartFile 转换成File 
     *  
     * @param multfile 原文件类型 
     * @return File 
     * @throws IOException 
     */  
    public static File multipartToFile(MultipartFile multfile) throws IOException {  
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
        //这个myfile是MultipartFile的  
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
        File file = fi.getStoreLocation();  
        //手动创建临时文件  
    
        return file;  
    } 

	
	

}
