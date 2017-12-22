package com.dly.app.service;

import com.dly.app.commons.baes.Result;
import com.dly.app.pojo.Collect;

/**
 * 提供所有图片接口
 * @author 12622
 *
 */

public interface MediaServce {
	public  Result getMoudles();
	public  Result getGroups(String index,String size,String userId);
	public Result getGroupsByMoudleId(String MoudleId,String index,String size,String userId);
	public Result getGroupByGroupId(String MoudleId);
	public Result search(String text);
	//用户添加收藏
	public Result userAddCollect(Collect collect);
	//获取用户收藏
	public Result getUserCollect(Collect  collect);
	//获取地区
	public Result getRegion();
	public Result getImages();
	public Result deleteImages(String id);
	
	
}
