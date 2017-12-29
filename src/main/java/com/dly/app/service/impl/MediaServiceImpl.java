package com.dly.app.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.fastdfs.FastdfsClient;
import com.dly.app.commons.redis.Cacheable;
import com.dly.app.commons.util.StringUtil;
import com.dly.app.commons.util.Util;
import com.dly.app.pojo.City;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Image;
import com.dly.app.pojo.Moudle;
import com.dly.app.pojo.Search;
import com.dly.app.service.MediaService;

@Service
public class MediaServiceImpl extends SuperClass  implements MediaService{
		
	@Resource
	public  Util  util;
	@Resource
	private FastdfsClient fast;
	@Override
	@Cacheable(fieldKey = {}, key = "getMoudles")
	public Result getMoudles() {
		List<Moudle> l=	mediaDao.getMoudles();
		
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", l);
			return new Result("true","0","返回成功","",jsonObject);
		}

	@Override
	//@Cacheable(fieldKey = {"#moudleId","#index"}, key = "getGroupsByMoudleId")
	public Result getGroupsByMoudleId(Group group) {
		JSONObject jsonObject =new JSONObject();
		List<Group> result=mediaDao.getGroupsByMoudleId(group);
		if(StringUtil.strIsNotEmpty(group.getUserId())) {
			for (int i = 0; i <result.size(); i++) {
				Collect collect=new Collect();
				collect.setGroupId(Integer.valueOf(result.get(i).getId()));
				collect.setUserId(Integer.valueOf(group.getUserId()));
				if(mediaDao.getCollectByUserIdAndGroupId(collect).size()>0) {
					result.get(i).setIssc("1");
				}else {
					result.get(i).setIssc("0");
				}
			}
		}
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}
	@Override
	@Cacheable(fieldKey = "#group", key = "getGroupByGroupId")
	public Result getGroupByGroupId(String group) {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result", mediaDao.getGroupByGroupId(group));
		return new Result("true","0","返回成功","",jsonObject);
	}

	@Override
	//@Cacheable(fieldKey = "#index", key = "getGroups")
	public Result getGroups(Group group) {
		
		
		List<Group> result=mediaDao.getGroups(group);
		if(StringUtil.strIsNotEmpty(group.getUserId())) {
			for (int i = 0; i <result.size(); i++) {
				Collect collect=new Collect();
				collect.setGroupId(Integer.valueOf(result.get(i).getId()));
				collect.setUserId(Integer.valueOf(group.getUserId()));
				if(mediaDao.getCollectByUserIdAndGroupId(collect).size()>0) {
					result.get(i).setIssc("1");
				}else {
					result.get(i).setIssc("0");
				}
			}
		}
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}

	@Override
	public Result search(Search search) {
		if(StringUtil.strIsNotEmpty(search.getText())) {
			search.setText("%"+search.getText()+"%");
		}
		List<Group> result=mediaDao.search(search.getText());
		if(StringUtil.strIsNotEmpty( search.getUserId())) {
			for (int i = 0; i <result.size(); i++) {
				Collect collect=new Collect();
				collect.setGroupId(Integer.valueOf(result.get(i).getId()));
				collect.setUserId(Integer.valueOf(search.getUserId()));
				if(mediaDao.getCollectByUserIdAndGroupId(collect).size()>0) {
					result.get(i).setIssc("1");
				}else {
					result.get(i).setIssc("0");
				}
			}
		}
		
		
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}


	@Override
	public Result getImages() {
		List<Image> result=mediaDao.getImages();
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}

	@Override
	public Result deleteImages(String id) {
		if("*".equals(id)) {            //如果是*  删除所有type图片
			List<Image> result=mediaDao.getImages();
			for (int i = 0; i < result.size(); i++) {
				String imageUrl=result.get(i).getImageUrl();
				String imageId=result.get(i).getId();
				String group=imageUrl.substring(1, imageUrl.indexOf("/",2));
				String url=imageUrl.substring( imageUrl.indexOf("/",2)+1, imageUrl.length());
				fast.delete(group, url);
				if(mediaDao.deleteImageById(imageId)>0) {
					System.out.println("删除id::"+result.get(i).getId());
				}
			}
		}else { //删除图片
			Image image=mediaDao.getImageById(id);
			String imageUrl=image.getImageUrl();
			String group=imageUrl.substring(1, imageUrl.indexOf("/",2));
			String url=imageUrl.substring( imageUrl.indexOf("/",2)+1, imageUrl.length());
			fast.delete(group, url);
			if(mediaDao.deleteImageById(id)>0) {
				System.out.println("删除id::"+id);
			}
		}
		return new Result("true","0","删除成功","");
	}

	@Override
	public Result getRegion() {
		Map<String,Object> map=new HashMap<String,Object>();
		List<City> region=mediaDao.getCitys();
		for (int i = 0; i <region.size(); i++) {
			region.get(i).setCountys(mediaDao.getCountysByCityId(region.get(i).getId()));	
		}
		map.put("city", region);

		return new Result("true","0","返回成功","",map);
	}

	@Override
	public Result getCarousel() {
		List<Image> region=mediaDao.getCarousel();
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", region);
		return new Result("true","0","返回成功","",jsonObject);
	}



}
