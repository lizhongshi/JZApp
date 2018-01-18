package com.dly.app.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dly.app.commons.baes.Result;

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
	public Result upLoadImage(MultipartFile file);
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public Result upLoadFile(MultipartFile file);
/**
 * 删除
 * @param group
 * @param fileAddr
 * @return
 */
	public Result delete(String group,String fileAddr);
	
	

}
