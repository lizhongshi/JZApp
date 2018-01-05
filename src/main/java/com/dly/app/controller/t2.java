package com.dly.app.controller;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class t2 implements Runnable{
	CountDownLatch cd1=new CountDownLatch(1300);
	@Override
	public void run() {
		try {
			cd1.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		HttpGet httpGet = new HttpGet("http://localhost:8888/app/media/group/1");
		 String res = "";  
	        try {  
	            // 执行请求  
	            HttpResponse getAddrResp = httpClient.execute(httpGet);  
	            HttpEntity entity = getAddrResp.getEntity();  
	            if (entity != null) {  
	                res = EntityUtils.toString(entity);  
	            }  
	           System.out.println("响应--->"+res);
	          
	        } catch (Exception e) {  
	        } finally {  
	            try {  
	                httpClient.close();  
	            } catch (IOException e) {  
	            }  
	        }  
		
	}
	
}
