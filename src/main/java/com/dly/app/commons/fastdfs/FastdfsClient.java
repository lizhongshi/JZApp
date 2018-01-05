package com.dly.app.commons.fastdfs;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dly.app.controller.UserController;

public class FastdfsClient {
	private static Logger log = Logger.getLogger(FastdfsClient.class);
	 private  String 	confFilename ; 
	 
	public String getConfFilename() {
		return confFilename;
	}
	public void setConfFilename(String confFilename) {
		this.confFilename = confFilename;
	}
	public void  init() {
		
		log.info("fastdfs---->初始化");
		try {
			ClientGlobal.init(confFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public String[] upLoad(CommonsMultipartFile file,Map<String,String> map) throws MyException, IOException {
		 
		 String fileIds[] =null;
	            TrackerClient tracker = new TrackerClient(); 

	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;

	 

	            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
	        	NameValuePair nvp [] =null;
	            if(null!=map) {
	            	
	            	 nvp  = new NameValuePair[map.size()];
//	          NameValuePair nvp = new NameValuePair("age", "18"); 
	            	int index=0;
	            	for (String obj : map.keySet()) {
	            		String value=map.get(obj);
	            		nvp[index++]=new NameValuePair(obj, value);
	            	}
	            }

	            
	            
	            
	            fileIds = storageClient.upload_file(file.getBytes(),
	            		file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length()), nvp);
	            log.info(fileIds.length); 
	            log.info("文件路径：" +"/"+ fileIds[0]+"/"+fileIds[1]); 
	    
	        return new String[] {fileIds[0],fileIds[1]};

	 }
	 	public  String[] upLoad(File file) throws MyException, IOException {
	InputStream input = new FileInputStream(file);

	byte[] byt = new byte[input.available()];

	input.read(byt);
		 String fileIds[] =null;

	        
	           

	 

	            TrackerClient tracker = new TrackerClient(); 
	            
	      
	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;
	            
	 

	            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 

//	          NameValuePair nvp = new NameValuePair("age", "18"); 

	            NameValuePair nvp [] = new NameValuePair[]{ 
	                    new NameValuePair("age", "18"), 
	                    new NameValuePair("sex", "male") 
	            }; 
	            
	            fileIds = storageClient.upload_file(byt,
	            		file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length()), nvp);
	            log.info(fileIds.length); 
	            log.info("文件路径：" +"/"+ fileIds[0]+"/"+fileIds[1]); 
	    
	        return new String[] {fileIds[0],fileIds[1]};

	 }
	 /**
	  * 下载文件
	  * @param fileAddr 文件路径
	  */
	    public void testDownload(String fileAddr) {

	        try {

	 

	           // ClientGlobal.init(conf_filename);

	 

	            TrackerClient tracker = new TrackerClient(); 

	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;

	 

	            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 

	            byte[] b = storageClient.download_file("group1", "M00/00/00/wKgRcFV_08OAK_KCAAAA5fm_sy874.conf"); 

	            System.out.println(b); 

	            IOUtils.write(b, new FileOutputStream("D:/"+UUID.randomUUID().toString()+".conf"));

	        } catch (Exception e) { 

	            e.printStackTrace(); 

	        } 

	    }

	     


	    public void testGetFileInfo(){ 

	        try { 

	            //ClientGlobal.init(conf_filename);

	 

	            TrackerClient tracker = new TrackerClient(); 

	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;

	 

	            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 

	            FileInfo fi = storageClient.get_file_info("group1", "M00/00/00/wKgRcFV_08OAK_KCAAAA5fm_sy874.conf"); 

	            System.out.println(fi.getSourceIpAddr()); 

	            System.out.println(fi.getFileSize()); 

	            System.out.println(fi.getCreateTimestamp()); 

	            System.out.println(fi.getCrc32()); 

	        } catch (Exception e) { 

	            e.printStackTrace(); 

	        } 

	    } 

	     

	    public void testGetFileMate(){ 

	        try { 

	           // ClientGlobal.init(conf_filename);

	 

	            TrackerClient tracker = new TrackerClient(); 

	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;

	 

	            StorageClient storageClient = new StorageClient(trackerServer, 

	                    storageServer); 

	            NameValuePair nvps [] = storageClient.get_metadata("group1", "M00/00/00/wKgRcFV_08OAK_KCAAAA5fm_sy874.conf"); 

	            for(NameValuePair nvp : nvps){ 

	                System.out.println(nvp.getName() + ":" + nvp.getValue()); 

	            } 

	        } catch (Exception e) { 

	            e.printStackTrace(); 

	        } 

	    } 

	     


	    public static void delete(String group,String url){ 

	        try { 

	          //  ClientGlobal.init(conf_filename);

	 

	            TrackerClient tracker = new TrackerClient(); 

	            TrackerServer trackerServer = tracker.getConnection(); 

	            StorageServer storageServer = null;

	 

	            StorageClient storageClient = new StorageClient(trackerServer, 

	                    storageServer); 

	            int i = storageClient.delete_file(group, url); 

	            System.out.println( i==0 ? "删除成功" : "删除失败:"+i); 

	        } catch (Exception e) { 

	            e.printStackTrace(); 

	        } 

	    }

}
