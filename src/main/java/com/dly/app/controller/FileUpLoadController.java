package com.dly.app.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.service.FastdfsService;

@RestController
@RequestMapping("file")
public class FileUpLoadController extends SuperClass {
	private static Logger log = Logger.getLogger(FileUpLoadController.class);
	@Resource
	private FastdfsService fastdfsService;
	 @PostMapping(value = "imageUpload",produces = "application/json;charset=UTF-8")
	 public Object imageFileUpload( MultipartFile[] file) throws IOException {
	        long  startTime=System.currentTimeMillis();
	        Result result = null;
	        for (int i = 0; i < file.length; i++) {
	        	
	        	System.out.println("fileName："+file[i].getOriginalFilename());
	        	
	        	result = fastdfsService.upLoadImage(file[i]);
	        }
		
		       log.info("上传文件返回===:"+result);
		       long  endTime=System.currentTimeMillis();
		       log.info("上传文件耗时===:"+(endTime-startTime));
				return result;
	    }
	 @PostMapping(value = "fileUpload",produces = "application/json;charset=UTF-8")
	 public Object  fileUpload( MultipartFile[] file) throws IOException {
	        long  startTime=System.currentTimeMillis();
	        Result result = null;
	        for (int i = 0; i < file.length; i++) {
				
	        	System.out.println("fileName："+file[i].getOriginalFilename());
	        	
	        	result = fastdfsService.upLoadFile(file[i]);
			}
		
		       log.info("上传文件返回===:"+result);
		       long  endTime=System.currentTimeMillis();
		       log.info("上传文件耗时===:"+(endTime-startTime));
				return result;
	    }
	 
	

}
