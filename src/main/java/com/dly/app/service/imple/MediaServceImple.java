package com.dly.app.service.imple;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.Util;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.fastdfs.FastdfsUtil;
import com.dly.app.commons.redis.CacheEvict;
import com.dly.app.commons.redis.Cacheable;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.Image;
import com.dly.app.pojo.Moudle;
import com.dly.app.pojo.Region;
import com.dly.app.service.MediaServce;

@Service
public class MediaServceImple extends SuperClass  implements MediaServce{
		
	@Resource
	public  Util  util;

	@Override
	@Cacheable(fieldKey = {}, key = "getMoudles")
	public Result getMoudles() {
		List<Moudle> l=	mediaDao.getMoudles();
		
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", l);
			return new Result("true","0","返回成功","",jsonObject);
		}

	@Override
	@Cacheable(fieldKey = {"#moudleId","#index"}, key = "getGroupsByMoudleId")
	public Result getGroupsByMoudleId(String moudleId,String index,String size,String userId) {
		
		Group group =new Group();
		group.setIndex(Integer.valueOf(index));
		group.setSize(Integer.valueOf(size));
		group.setMoudleId(moudleId);
		JSONObject jsonObject =new JSONObject();
		
		List<Group> result=mediaDao.getGroupsByMoudleId(group);
		for (int i = 0; i <result.size(); i++) {
			if(userId.equals(result.get(i).getUserId())) {
				result.get(i).setIssc("1");
			}else {
				result.get(i).setIssc("0");
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
	@Cacheable(fieldKey = "#index", key = "getGroups")
	public Result getGroups(String index,String size,String userId) {
		Group group =new Group();
		group.setIndex(Integer.valueOf(index));
		group.setSize(Integer.valueOf(size));
		List<Group> result=	mediaDao.getGroups(group);
		if(null!=userId) {
			for (int i = 0; i <result.size(); i++) {            //判断用户是否收藏
				if(userId.equals(result.get(i).getUserId())) {
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
	public Result search(String text) {
		if(!"".equals(text)&&null!=text) {
			text="%"+text+"%";
		}
		List<Group> result=mediaDao.search(text);
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}

	@Override
	@CacheEvict(fieldKey = { "#collect.userId" }, key = "getUserCollect")
	public Result userAddCollect(Collect collect) {
		int i= mediaDao.userAddCollect(collect);
		if(i>0) {
			return new Result("true","0","收藏成功","");
		}
		
		return new Result("false","99","收藏失败","");
	}
	@Override
	@Cacheable(fieldKey = { "#collect.userId" }, key = "getUserCollect")
	public Result getUserCollect(Collect collect) {
		List<Collect> result=mediaDao.getCollectByUserId(collect);
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
				FastdfsUtil.delete(group, url);
				if(mediaDao.deleteImageById(imageId)>0) {
					System.out.println("删除id::"+result.get(i).getId());
				}
			}
		}else { //删除图片
			Image image=mediaDao.getImageById(id);
			String imageUrl=image.getImageUrl();
			String group=imageUrl.substring(1, imageUrl.indexOf("/",2));
			String url=imageUrl.substring( imageUrl.indexOf("/",2)+1, imageUrl.length());
			FastdfsUtil.delete(group, url);
			if(mediaDao.deleteImageById(id)>0) {
				System.out.println("删除id::"+id);
			}
		}
		return new Result("true","0","删除成功","");
	}

	@Override
	public Result getRegion() {
		List<Region> result=mediaDao.getRegion();
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}
	

}
