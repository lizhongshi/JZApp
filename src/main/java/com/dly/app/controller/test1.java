//package com.dly.app.controller;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.concurrent.CountDownLatch;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//
//
//
//public class test1{
//	  CountDownLatch  cd1=new CountDownLatch(2000);
//	  @Test
//	public  void oooooo() {
//	System.out.println(131321);
//		for (int i = 0; i <2000; i++) {
//			//pdx pdx=new pdx();
//				new Thread(new pdx()).start();;
//
//			cd1.countDown();
//	}
//		try {
//			Thread.currentThread().join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
//
//	
//private	  class  pdx implements Runnable{
//		
//		@Override
//		public void run() {
//			try {
//				cd1.await();
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			CloseableHttpClient httpClient = HttpClients.createDefault(); 
//			HttpGet httpGet = new HttpGet("http://39.106.129.135:8080/app/media/group/1");
//			String res = "";  
//			try {  
//				// 执行请求  
//				HttpResponse getAddrResp = httpClient.execute(httpGet);  
//				HttpEntity entity = getAddrResp.getEntity();  
//				if (entity != null) {  
//					res = EntityUtils.toString(entity);  
//				}  
//				System.out.println("响应--->"+res);
//				
//			} catch (Exception e) {  
//			} finally {  
//				try {  
//					httpClient.close();  
//				} catch (IOException e) {  
//				}  
//			}  
//			
//		}
//	}
//	
//
//}
//
//
