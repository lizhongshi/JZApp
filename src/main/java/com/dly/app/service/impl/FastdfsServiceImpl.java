package com.dly.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.fastdfs.FastdfsClient;
import com.dly.app.pojo.User;
import com.dly.app.service.FastdfsService;
@Service("fastdfsService")
public class FastdfsServiceImpl extends SuperClass implements FastdfsService{
	@Resource
	private FastdfsClient fast;
	private static Logger log = Logger.getLogger(FastdfsServiceImpl.class);
	public Result upLoad(String tokendid,CommonsMultipartFile file) {
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
			m.put("sss", "sssssss");
			 path=	fast.upLoad(file,m);
			user.setIconUrl("/"+path[0]+"/"+path[1]);
			userDao.changeUserInfo(user);
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
	public Result upLoad(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
