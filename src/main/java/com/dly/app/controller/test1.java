package com.dly.app.controller;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dly.app.pojo.User;
import com.dly.app.service.TripartiteService;


@Controller
@RequestMapping("sss")
public class test1{
	@Resource
	TripartiteService tripartiteService;
	private int index=20002;
	  CountDownLatch  cd1=new CountDownLatch(index);
	  @RequestMapping("sss")
	public  void oooooo() {
		  User user=new User();
			user.setPhone("133333333");
			user.setQq("aaaaaaaaa");
			user.setPassword("22");
			tripartiteService.bind(user);
		}

	
private	  class  pdx implements Runnable{
		
		@Override
		public void run() {
			User user=new User();
			user.setPhone("133333333");
			user.setQq("aaaaaaaaa");
			try {
				cd1.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("111");
			tripartiteService.bind(user);
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
			
		}
	}
	

}


