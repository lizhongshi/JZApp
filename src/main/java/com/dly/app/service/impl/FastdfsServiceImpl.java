package com.dly.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.fastdfs.FastdfsClient;
import com.dly.app.dao.MediaDAO;
import com.dly.app.pojo.Image;
import com.dly.app.pojo.User;
import com.dly.app.service.FastdfsService;
@Service("fastdfsService")
public class FastdfsServiceImpl extends SuperClass implements FastdfsService{
	@Resource
	private FastdfsClient fast;
	private static Logger log = Logger.getLogger(FastdfsServiceImpl.class);
	public Result upLoadUserIcon(String tokendid,CommonsMultipartFile file,String type) {
		User user =new User();
		String[] path=null;
		user.setUserId(tokendid);
			try {
			String userid=	redisUtil.getValue(tokendid);
			if(userid!=null) {
				user.setUserId(userid);
			}else {
				return new Result("false", "99", "上传图片失败", "");
			}
			Map<String,String> m=new HashMap<String,String>();
			m.put("type", type);
			 path=	fast.upLoad(file,m);
			user.setIconUrl("/"+path[0]+"/"+path[1]);
			//插入
			userDao.updateUserInfo(user);
			} catch (MyException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				return new Result("false", "99", "上传图片失败", e.getMessage());
			}
			//返回用户图片
			JSONObject json=new JSONObject();
			json.put("iconUrl", "/"+path[0]+"/"+path[1]);
			return new Result("true", "0", "上传图片成功", "",json);
	}

	@Override
	public Result delete(String group,String fileAddr) {
		
		return null;
	}

	@Override
	public Result upLoadImage(CommonsMultipartFile file) {
		String[] path=null;
		try {
			System.out.println(file.getContentType());
			 path=	fast.upLoad(file,new HashMap());
			 BufferedImage sourceImg =ImageIO.read(file.getInputStream()); 
				Image image=new Image();
				image.setImageUrl("/"+path[0]+"/"+path[1]);
				image.setGroup(path[0]);
				image.setHeight(String.valueOf(sourceImg.getHeight()));
				image.setWidth(String.valueOf(sourceImg.getWidth()));
				image.setType("0");
				mediaDao.addImage(image);
		} catch (MyException e) {
			e.printStackTrace();
			return new Result("false", "99", "上传图片失败", "",e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result("false", "99", "上传图片失败", "",e.getMessage());
		}
		return new Result("true", "0", "上传图片成功", "");
	}

	@Override
	public Result upLoadFile(CommonsMultipartFile file) {
				
		return null;
	}

}
