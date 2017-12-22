package com.dly.app.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.dly.app.commons.redis.Cacheable;
import com.dly.app.pojo.Comment;
import com.dly.app.pojo.User;
import com.dly.app.pojo.UserInfo;
@Component
public interface UserDAO {
	//public User login(User user);//登录
	public User getUserByUserType(User user);
	public int register(User user);//注册
	/**
	 * 通过用户名查询角色信息
	 * @param userName
	 * @return
	 */
	public Set<String> getRoles(String userName);
	
	/**
	 * 通过用户名查询权限信息
	 * @param userName
	 * @return
	 */
	public Set<String> getPermissions(String userName);
	/**
	 * 修改用户登录状态
	 * @param username
	 * @return
	 */
	
	/**
	 * 用户评论
	 * @param in
	 * @return
	 */
	
	public int insertComment(Comment in) ;
	
	/**
	 * 获取页面所有评论
	 * @param in
	 * @return
	 */
	 @Cacheable(fieldKey = "#in.groupId", key = "getCommentByGroupid")
	public List<Comment> getCommentByGroupid(Comment in);
	/**
	 * 获取用户信息
	 * @param username
	 * @return
	 */
	 
	public UserInfo getUserInfo(User user);
	
	public int upstruts (String username);
	public int changeUserInfo(User user);
}
