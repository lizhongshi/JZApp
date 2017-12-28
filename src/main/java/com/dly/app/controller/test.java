package com.dly.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dly.app.commons.util.StringUtil;

import redis.clients.jedis.Jedis;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;
@RequestMapping("test")
public class test   implements  Runnable {
	

	public static void main(String[] args) {
		
//		Thread t1=null;
//		for (int i = 0; i <1000; i++) {
//			test t=new test();
//			
//			 t1=new Thread(t);
//			 t1.start();
//		}
//	Result r=	getResult();
//	r.setCode("2");
//	System.out.println(r);
//	Result r1=	getResult();
		String str="s";
		
		if(StringUtil.strIsNotEmpty(str)) {
			System.out.println("不是空");
		}
//				Jedis jedis =new Jedis("39.106.129.135",6379);
//				jedis.auth("200814");
//				jedis.hset("aaa", "bbb", "ccc");
//			//	jedis.expire("aaa", 500);
//				System.out.println(jedis.ttl("aaa"));
//				System.out.println(jedis.hget("aaa", "bbb"));
				//System.out.println(jedis.hget("getUserInfo", "26"));
				
				
				
//				String s="/group1/M00/00/02/wKgBUFovf8qAR4OTAAni0NY1wfs935.png";
//				System.out.println(s.substring(s.indexOf("/",2), s.length()));
				
//				System.out.println(jedis.exists("9073a3dd255d416bb4b0c9f851665622"));
//				System.out.println(jedis.ttl("9073a3dd255d416bb4b0c9f851665622"));
//				System.out.println(jedis.del("9073a3dd255d416bb4b0c9f851665622"));
				//jedis.zadd("comment:11", 5, "userid:5");
//				jedis.del("comment:11");
//				jedis.zrem("comment:11","userid:*");
				
//				for (int i = 0; i < 100; i++) {
//					Date d=new Date();
//					Long time =d.getTime();	
//					jedis.zadd("comment:11", time, "userid:"+i);
//				}
				Date d=new Date();
				System.out.println(d.getTime());
				
//				
//				jedis.set("comment:11", "ddzx");
//				System.out.println(jedis.zrange("comment:11", 0, 100));
		
	//	File f=new File("C:\\Users\\12622\\Desktop\\images");
		
//		try {
//			FastdfsUtil.upLoad(f);
//		} catch (MyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		
		
		
		//showDirectory(f,"");
//					
//				Long s=jedis.getDB();
//				System.out.println(jedis.get("15932218252"));
//				jedis.hset("21", "ss", "dd");
//				//jedis.hset("21", "dd", "ddx");
//				System.out.println(jedis.hkeys("21"));
//				System.out.println(jedis.hgetAll("21"));
//				System.out.println(jedis.hmget("21", "ss"));
				//jedis.del("21");
//				jedis.sadd("wd","1");
////			Set<String> s1=	jedis.smembers("wd");
////			System.out.println(s1);
//				jedis.set("sd", "sdas");
//			System.out.println(jedis.ttl("e8eebc5897384ebdae831231e6b708bb"));
//				System.out.println(jedis.get("asda"));
//				jedis.expire("2703825ff9984af3a729e125ce59af71",-1);
				
//				HashMap map=new HashMap();
//				map.put("a", "22323");
//				map.put("uuid", "22323");
//				JSONObject o=JSONObject.parseObject(Util.mapToJson(map));
//				Result r=new Result("", "", "", "",o);
//				System.out.println(JSON.toJSONString(r));
//				System.out.println(o);
		
			
				//System.out.println(jedis.);
//				User user=new User();
//				//user.setUser_id("23");
//				//jedis.set("user", JSONObject.toJSONString(user));
//				System.out.println(jedis.get("23"));
//				User u=JSONObject.parseObject(jedis.get("user"), User.class);
//				System.out.println(s);
//				//jedis.set("ss","你好");
//					System.out.println(u);
//		String a="http://localhost:8088/app/personal/login";
//		System.out.println(a.lastIndexOf("/"));
//		String b=a.substring(a.lastIndexOf("/")+1,a.length());
//		System.out.println(b);
//		String a=null;
//		if(a==null) {
//			System.out.println("dsd");
//		}
//		if(a=="") {
//			System.out.println("dsd");
//		}
//
////		str(a);
//		System.out.println(DataType.Data_Set);
//          RedisUtil r=new RedisUtil();
//          r.setCacheSet("", new Set());
		
		
//		Random r=new Random();
//		StringBuffer s=new StringBuffer();
//		for (int i = 0; i < 4; i++) {
//			s.append(	r.nextInt(10));
//		}
//	String d=s.toString();
//		System.out.println(d);
		
		
		
		
		//upload("C:\\Users\\12622\\Desktop\\1512538337(1).jpg", "/home", connectFTP("39.106.129.135", 21, "root", "Ab200814"));
	}
	public static void showDirectory(File file,String ss){
        File[] files = file.listFiles();

        for(File a:files){
        	if(!a.isDirectory()) {
        		   System.out.println("文件路径"+a.getAbsolutePath());
        		   System.out.println("文件后缀"+a.getName().substring(a.getName().lastIndexOf(".")+1, a.getName().length()));
        		   System.out.println("+++++++++++="+ss);
        	}
         
            
            if(a.isDirectory()){
            	System.out.println("文件夹"+a.getName());
            	ss=a.getName();
                showDirectory(a,ss);
            }
        }
    }
 static	void str(String a) {
		
	}

	public void run() {
		System.out.println(Thread.currentThread().getName());
		HttpClient httpClient = HttpClients.createDefault();
		
		Thread t1=null;
		for (int i = 0; i <1000; i++) {
			test t=new test();
			
			 t1=new Thread(t);
			 t1.start();
		}
	}
	@GetMapping(value="f22")
	public void sd() {
		
	}
	public static void upload(String localFile, String ftpFile, FtpClient ftp) {  
        OutputStream os = null;  
        FileInputStream fis = null;  
        try {  
            // 将ftp文件加入输出流中。输出到ftp上  
            os = ftp.putFileStream(ftpFile);  
            File file = new File(localFile);  
  
            // 创建一个缓冲区  
            fis = new FileInputStream(file);  
            byte[] bytes = new byte[1024];  
            int c;  
            while((c = fis.read(bytes)) != -1){  
                os.write(bytes, 0, c);  
            }  
            System.out.println("upload success!!");  
        } catch (FtpProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(os!=null) {  
                    os.close();  
                }  
                if(fis!=null) {  
                    fis.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
	public static FtpClient connectFTP(String url, int port, String username, String password) {  
        //创建ftp  
        FtpClient ftp = null;  
        try {  
            //创建地址  
            SocketAddress addr = new InetSocketAddress(url, port);  
            //连接  
            ftp = FtpClient.create();  
            ftp.connect(addr);  
            //登陆  
            ftp.login(username, password.toCharArray());  
            ftp.setBinaryType();  
        } catch (FtpProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return ftp;  
    }  

}
