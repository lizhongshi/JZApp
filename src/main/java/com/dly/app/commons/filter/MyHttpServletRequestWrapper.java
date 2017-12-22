package com.dly.app.commons.filter;  
  
import java.io.BufferedReader;  
import java.io.ByteArrayInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletRequestWrapper;  
import org.springframework.util.StreamUtils;  
  
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper{  
  
    private byte[] requestBody = null;   
      
    public MyHttpServletRequestWrapper(HttpServletRequest request) {  
          
        super(request);  
          
        //缓存请求body  
        try {  
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());  
           
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
    }  
    public MyHttpServletRequestWrapper(HttpServletRequest request,byte[] requestBody) {  
        
        super(request);  
          
        //缓存请求body  
        
            this.requestBody =requestBody;  
           
         
    }
    
    
      
    /** 
     * 重写 getInputStream() 
     */  
    @Override  
    public ServletInputStream getInputStream() throws IOException {  
        if(requestBody == null){  
            requestBody= new byte[0];  
        }  
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);    
        return new ServletInputStream() {    
             
            public int read() throws IOException {    
                return bais.read();    
            }

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener arg0) {
				// TODO Auto-generated method stub
				
			}    
        };    
    }  
  
    /** 
     * 重写 getReader() 
     */  
    @Override  
    public BufferedReader getReader() throws IOException {  
        return new BufferedReader(new InputStreamReader(getInputStream()));    
    }  
    public static void main(String[] args) {
    	
    	byte[] by=new  byte[50] ;
	}
      
  
}  