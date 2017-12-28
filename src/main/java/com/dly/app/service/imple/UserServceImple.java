package com.dly.app.service.imple;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.redis.CacheEvict;
import com.dly.app.commons.redis.Cacheable;
import com.dly.app.commons.util.StringUtil;
import com.dly.app.commons.util.Util;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Comment;
import com.dly.app.pojo.Group;
import com.dly.app.pojo.User;
import com.dly.app.pojo.UserInfo;
import com.dly.app.service.UserService;
@Service("userService")
public class UserServceImple extends SuperClass implements UserService {
	private static Logger log = Logger.getLogger(UserServceImple.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result login(User user) {
		User perUser=userDao.getUserByUserType(user);
		if(perUser!=null) {//把用户对象放进缓存
	
			log.info(perUser.toString());
			if("0".equals(perUser.getStruts())) {//判断用户状态 1:可以登录 0:用户被冻结
				return new Result("false","99","您的账户已被冻结!","") ;
			}
			if(redisUtil.cacheValue(perUser.getPhone(), JSONObject.toJSONString(perUser), 500)) {
				Subject subject=SecurityUtils.getSubject();
				String salt=perUser.getSalt();//获取用户盐
				String password=Util.Md5(user.getPassword(), salt);
				UsernamePasswordToken token=new UsernamePasswordToken(perUser.getPhone(), password);//传入密码
				System.out.println("账户"+perUser.getUsername()+"++++"+"密码++++++"+perUser.getPassword());
				try {
					subject.login(token);//登录
				}catch(AuthenticationException ae) {
					ae.printStackTrace();
					return new Result("false","99","用户名或密码错误!","") ;
				}finally {
					redisUtil.deleteKey(perUser.getPhone());//删除缓存
				}
			}else {
				return new Result("false","99","缓存数据失败!","") ;
			}
			
		}else {//用户不存在
			return new Result("false","99","用户名或密码错误!","") ;
		}
		String tokenid=Util.getUUID();
		Map map=new HashMap();
		map.put("nikename", perUser.getNickname());//userid
		map.put("tokenId", tokenid);//tokenid
		map.put("userId", perUser.getUserId());//userid
		map.put("iconUrl", perUser.getIconUrl());//头像地址
		map.put("email", perUser.getEmail());//邮箱地址
		map.put("sex", perUser.getEmail());//
		map.put("phone", perUser.getPhone());//
		if(redisUtil.cacheValue(tokenid, String.valueOf(perUser.getUserId()),15000000)) {//&&redisUtil.hashSetAll(perUser.getUserid(), map,RedisUtil.USER)
			return new Result("true","0","登录成功","",Util.mapToJsonObj(map)) ;	
		}else {
			return new Result("false","99","数据缓存失败","") ;	
		}
			/**
			 * 留着等手机用
			 */
//			if(us.getStruts().equals("1")) {//用户状态被冻结
//				return new Result("false","98","用户已登录","") ;
//			}else {//未登录
//				userDao.upstruts(us.getUsername());//修改用户状态
//			}

	}

	public Result register(User user) {
		User  us= userDao.getUserByUserType(user);
			if(null!=us){	
				return new Result("false","99","已经存在用户","") ;
			}
//			if(!user.getVerificationCode().equals(redisUtil.getValue(user.getPhone()))) {
//				return new Result("false","99","验证码错误","") ;
//			}
		if(!user.getVerificationCode().equals(redisUtil.getValue(user.getPhone()))) {
			return new Result("false","99","验证码错误","") ;
		}
		
		//生成盐值
		String salt=Util.getUUID();
		user.setSalt(salt);
		//user.setCreationTime(new Date);
		user.setPassword(Util.Md5(user.getPassword(), salt));
		System.out.println(user);
		int lin=userDao.register(user);	
		if(lin>0) {
			return new Result("true","0","注册成功","") ;
		}else {
			return new Result("false","99","注册失败","") ;
		}
		
		
	
		
	}

	public Set<String> getRoles(String userName) {
		return userDao.getRoles(userName);
	}

	public Set<String> getPermissions(String userName) {
		return userDao.getPermissions(userName);
	}
	@Override
	public Result logout(String tokenid) {
		redisUtil.deleteKey(tokenid);
		return new Result("true","0","已经退出登录","");
	}
	

	@Override
	@CacheEvict(fieldKey = "#user.userId", key = "getUserInfo")
	public Result changeUserInfo(User user) {
		
		User userIn =userDao.getUserByUserType(user);
		int i=0;
		if(userIn!=null) {
			switch (user.getFfbm()) {
			case "password":{
				log.info("修改密码参数+++++++"+user);
				 if(user.getPassword()!=null&&user.getNewPassword()!=null) {//修改密码
						String oldPass=userIn.getPassword();
						String salt=userIn.getSalt();
						if(oldPass.equals(Util.Md5(user.getPassword(), salt))) {
							user.setNewPassword(Util.Md5(user.getNewPassword(), salt));
							 i=userDao.changeUserInfo(user);
						}else {
							return new Result("false","99","密码错误","");
						}
					}
			}
				break;
			case "nickname":{
				log.info("修改昵称参数+++++++"+user);
				if(user.getNickname()!=null) {//修改昵称
					 i=userDao.changeUserInfo(user);
				}			
			}
				break;
			case "phone":{
				log.info("修改手机号参数+++++++"+user);
				if(user.getVerificationCode()!=null&&user.getNewPhone()!=null) {//如果验证码和手机号不等于空 进行判断验证码
					if(!user.getVerificationCode().equals(redisUtil.getValue(user.getNewPhone()))){//修改手机号
						return new Result("false","99","验证码错误","");
					}else {
						 i=userDao.changeUserInfo(user);
					}
				}
			}
				break;
			case "birthDate":{
				log.info("修改出生日期参数+++++++"+user);
				if(user.getBirthDate()!=null) {
					i=userDao.changeUserInfo(user);
				}
			}
				break;
			case "sex":{
				log.info("修改性别参数+++++++"+user);
				if(user.getSex()!=null) {
					i=userDao.changeUserInfo(user);
				}
			}
				break;
			default:
				break;
			}
		}else {        																	
			return new Result("false","99","用户信息验证失败","");
		}
		if(i>0) {
			return new Result("true","0","修改成功","");
		}else {
			return new Result("false","99","修改失败","");	
		}
	}

	@CacheEvict(fieldKey = "#in.groupId", key = "getComment")
	public Result insertComment(Comment in) {
		try {
			int i=userDao.insertComment(in);
			if(i>0) {
				return new Result("true","0","评论成功","");
			}
			return new Result("false","99","评论失败","");	
			
		}catch(Exception e) {
			return new Result("false","99","评论失败",e.getMessage());	
		}
	}

	@Cacheable(fieldKey = "#in.groupId", key = "getComment")
	public Result getComment(Comment in) {
		List<Comment>  comment=new ArrayList<Comment> ();
		try {
			comment=	userDao.getCommentByGroupid(in);
		}catch(Exception e) {
			return new Result("false","99","获取评论失败",e.getMessage());
		}
		JSONObject json=new JSONObject();
			json.put("result", comment);
		return new Result("true","0","获取评论成功","",json);
	}

	@Cacheable(fieldKey = "#user.userId", key = "getUserInfo")
	public Result getUserInfo(User user) {
		UserInfo u= userDao.getUserInfo(user);
		System.out.println("---------"+u);
		
		JSONObject json =new JSONObject();
		json.put("user", u);
		return new Result("true","0","","",json);
	}
	
	@Override
	@Cacheable(fieldKey = { "#collect.userId","#collect.groupId" }, key = "getUserCollect")
	public Result getUserCollect(Collect collect) {
		List<Group> result=userDao.getCollectByUserId(collect);
		for (int i = 0; i <result.size() ; i++) {
			result.get(i).setIssc("0");
		}
		
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("result", result);
		return new Result("true","0","返回成功","",jsonObject);
	}
	@Override
	@CacheEvict(fieldKey ={ "#collect.userId","#collect.groupId" }, key = "getUserCollect")
	public Result userDeleteCollect(Collect collect) {
		int i=userDao.deleteCollect(collect);
		if(i>0) {
			return new Result("true","0","删除成功","");
		}else {
			return new Result("false","99","删除失败","");
		}
	
	}
	
	@Override
	@CacheEvict(fieldKey = { "#collect.userId","#collect.groupId" }, key = "getUserCollect")
	public Result userAddCollect(Collect collect) {
		int i= userDao.addCollect(collect);
		if(i>0) {
			return new Result("true","0","收藏成功","");
		}
		
		return new Result("false","99","收藏失败","");
	}
	

}
