package com.dly.app.service;

import com.dly.app.commons.baes.Result;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Search;

/**
 * 提供所有图片接口
 * @author 12622
 *
 */

public interface MediaServce {
	public  Result getMoudles();
	public  Result getGroups(Group group);
	public Result getGroupsByMoudleId(Group group);
	public Result getGroupByGroupId(String MoudleId);
	//搜索文章
	public Result search(Search search);
	//获取地区
	public Result getRegion();
	//获取所有后台图片
	public Result getImages();
	//删除图片
	public Result deleteImages(String id);
	
	public Result getCarousel();
	
}
