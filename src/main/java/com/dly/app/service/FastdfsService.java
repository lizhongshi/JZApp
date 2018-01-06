package com.dly.app.service;

import java.io.File;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dly.app.commons.baes.Result;
import com.dly.app.pojo.User;

public interface FastdfsService {
	/**
	 * 上传
	 * @param file 
	 * @return
	 */
	public Result upLoadUserIcon(String id,CommonsMultipartFile file,String type);
	/**
	 * 
	 * @param file
	 * @return
	 */
	public Result upLoadImage(CommonsMultipartFile file);
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public Result upLoadFile(CommonsMultipartFile file);
/**
 * 删除
 * @param group
 * @param fileAddr
 * @return
 */
	public Result delete(String group,String fileAddr);
	
	

}
