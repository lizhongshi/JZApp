package com.dly.app.commons.shiro;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.dao.UserDAO;
import com.dly.app.pojo.User;
import com.dly.app.service.UserService;
@Component
public class MyRealm  extends AuthorizingRealm{
	private static Logger log = Logger.getLogger(MyRealm.class);
	
	@Resource
	private UserService  userService;
	@Resource
	public RedisCacheUtil redisUtil;
	/**
	 * 为当限前登录的用户授予角色和权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName=(String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.getRoles(userName));
		authorizationInfo.setStringPermissions(userService.getPermissions(userName));
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的用户
	 */

	
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
			User postUser=JSONObject.parseObject(redisUtil.getValue(userName), User.class);
			if(postUser!=null) {
				//log.info("账户"+user.getUsername()+"密码后+++++"+user.getPassword());
				
				 AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(postUser.getPhone(),postUser.getPassword(),"xx");
				 return authcInfo;
			}else {
				return null;		
			}
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		MyRealm.log = log;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RedisCacheUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisCacheUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

}
