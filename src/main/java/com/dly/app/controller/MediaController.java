package com.dly.app.controller;


import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.util.Util;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Search;
/**
 * 媒体服务接口
 * @author 12622
 *
 */
@RequestMapping("media")
@RestController
public class  MediaController  extends SuperClass{
	@Resource
	public  Util  util;
	//获取所有模块
	@GetMapping(value="moudles",produces = "application/json;charset=UTF-8")
	public  Object getMoudleS() {
		return 
		mediaServce.getMoudles();
	}
	//获取所有文章
	@GetMapping(value="groups",produces = "application/json;charset=UTF-8")
	public  Object getGroups(Integer index,Integer size,String userId) {
		Group group=new Group();
		group.setIndex(index);
		group.setSize(size);
		group.setUserId(userId);
		return mediaServce.getGroups(group);
	}
	//根据文章获取内容
	@GetMapping(value="group/{groupId}",produces = "application/json;charset=UTF-8")
	public  Object getGroup(@PathVariable String groupId) {
		return 
		mediaServce.getGroupByGroupId(groupId);
	}
	//根据模块获取文章
	@GetMapping(value="groups/{moudleId}",produces = "application/json;charset=UTF-8")
	public  Object getGroupsByMoudleId(@PathVariable String moudleId,Integer index,Integer size,String userId) {
		Group group=new Group();
		group.setMoudleId(moudleId);
		group.setIndex(index);
		group.setSize(size);
		group.setUserId(userId);
		return mediaServce.getGroupsByMoudleId(group);
	}
	//搜索文章
	@GetMapping(value="search",produces = "application/json;charset=UTF-8")
	public  Object search( Search search) {
		System.out.println("搜索文章----->"+search);
		return mediaServce.search(search);
	}

	@GetMapping(value="images",produces = "application/json;charset=UTF-8")
	public  Object getImages() {
		
		return mediaServce.getImages();
	}
	 
	//上传图片
	 @GetMapping(value = "test1",produces = "application/json;charset=UTF-8")
	 public String  test1(String fileaddr) throws IOException {
		 System.out.println("controller-->"+fileaddr);
		 util.showDirectory(new File("C:\\Users\\12622\\Desktop\\images"),"");
	  
				return fileaddr;  
	    }
	 //删除所有背景图片
	 @DeleteMapping(value = "deleteImage/{id}",produces = "application/json;charset=UTF-8")
	 public Object  deleteImage(@PathVariable String id) throws IOException {
		 System.out.println("controller-->"+id);
	  
				return mediaServce.deleteImages(id);  
	    }
	 
	 //获取所有市,区县
	 @GetMapping(value = "region",produces = "application/json;charset=UTF-8")
	 public Object  getRegion() throws IOException {
		
				return mediaServce.getRegion();  
	    }
	 
	 //获取轮播图
	 @GetMapping(value = "carousel",produces = "application/json;charset=UTF-8")
	 public Object  getCarousel() throws IOException {
		
				return mediaServce.getCarousel();  
	    }
	 
	 
	 
	 
	 
	
	
}
