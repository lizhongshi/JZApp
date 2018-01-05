package com.dly.app.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	 public Object imageFileUpload(@RequestParam("file") CommonsMultipartFile file) throws IOException {
	        long  startTime=System.currentTimeMillis();
	        System.out.println("fileName："+file.getOriginalFilename());
			Result result = null;

				result = fastdfsService.upLoadImage(file);
		
		       log.info("上传文件返回===:"+result);
		       long  endTime=System.currentTimeMillis();
		       log.info("上传文件耗时===:"+(endTime-startTime));
				return result;
	    }
	 @PostMapping(value = "fileUpload",produces = "application/json;charset=UTF-8")
	 public Object  fileUpload(@RequestParam("file") CommonsMultipartFile file) throws IOException {
	        long  startTime=System.currentTimeMillis();
	        System.out.println("fileName："+file.getOriginalFilename());
			Result result = null;

				//result = fastdfsService.upLoadFile(file);
		
		       log.info("上传文件返回===:"+result);
		       long  endTime=System.currentTimeMillis();
		       log.info("上传文件耗时===:"+(endTime-startTime));
				return result;
	    }
	

}
