package com.dly.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dly.app.commons.baes.Result;
import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.commons.util.Util;
import com.dly.app.dao.TripartiteDAO;
import com.dly.app.dao.UserDAO;
import com.dly.app.pojo.User;
import com.dly.app.service.TripartiteService;

@Service
public class TripartiteServiceImpl implements TripartiteService {
	@Resource
	UserDAO userDAO;
	@Resource
	TripartiteDAO  tripartiteDAO;
	@Resource
	public RedisCacheUtil redisUtil;
	@Override
	public Result login(User userIn) {
		User userOut=userDAO.getUserByUserType(userIn);
		if(userOut==null) {//未绑定手机号
			return  new Result("true","1","未绑定手机号","");
		}else {//已经绑定手机号
			String tokenid=Util.getUUID();
			redisUtil.cacheValue(tokenid, userOut.getUserId(),15000000);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("nickname", userOut.getNickname());//userid
			map.put("tokenId", tokenid);//tokenid
			map.put("userId", userOut.getUserId());//userid
			map.put("iconUrl", userOut.getIconUrl());//头像地址
			//map.put("email", userOut.getEmail());//邮箱地址
			map.put("sex", userOut.getEmail());//
			map.put("phone", userOut.getPhone());//
			return  new Result("true","0","登录成功","",Util.mapToJsonObj(map));
		}
	}
	@Override
	public Result bind(User user) {
		try {
		if(!user.getVerificationCode().equals(redisUtil.getValue(user.getPhone()))) {
			return new Result("false","99","验证码错误","");
		}
		redisUtil.deleteKey(user.getPhone());//删除缓存
		if(tripartiteDAO.getUserByPhone(user.getPhone())==null) { //没有用户,注册
			String salt=Util.getUUID();
			user.setPassword(Util.Md5(user.getPassword(), salt)); 
			user.setSalt(salt);
			if(tripartiteDAO.register(user)>0) {//
				return  new Result("true","0","绑定成功","");
			}else {
				return  new Result("false","99","绑定失败","");
			}
		}else {//有用户,更新
				if(tripartiteDAO.bind(user)>0) {
					return  new Result("true","0","绑定成功","");
				}else {
					return  new Result("false","99","绑定失败","");
				}
			}
		}catch(Exception e) {
		e.printStackTrace();
		return  new Result("false","99","绑定失败",e.getMessage());
		}
	}
}
