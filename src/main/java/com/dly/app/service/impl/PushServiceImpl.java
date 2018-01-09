package com.dly.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dly.app.commons.baes.Result;
import com.dly.app.commons.jpush.JpushUtil;
import com.dly.app.dao.TPushMapper;
import com.dly.app.pojo.TPush;
import com.dly.app.pojo.TPushExample;
import com.dly.app.pojo.TPushExample.Criteria;
import com.dly.app.service.PushService;
@Service
public class PushServiceImpl  implements PushService{
	
	@Resource
	TPushMapper tPushMapper;
	@Resource
	JpushUtil jpushUtil;
	@Override
	public Result push(String body,String title) {
		if("".equals(body.trim())) {
			return new Result("false","99","推送消息不能为空","");
		}
	 	try {
			 jpushUtil.push(body);
		if(	tPushMapper.insert(new TPush(body,title))>0) {
			return new Result("true","0","推送成功","");
		}else {
			return new Result("false","99","推送失败","");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result("false","99","推送失败",e.getMessage());
		} 

	}
	@Override
	public Result get(Integer userId) {
		TPushExample  TPushExample=new TPushExample();
		Criteria criteria	=TPushExample.createCriteria();
		//criteria.andDateBetween(, value2);
	 List<TPush> result=	tPushMapper.getPushByUserId(userId);
	 	
		
		return new Result("true","0","成功","",result);
	}

}
