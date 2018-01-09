package com.dly.app.service;

import java.util.Set;

import com.dly.app.commons.baes.Result;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Comment;
import com.dly.app.pojo.User;
import com.dly.app.pojo.UserInfo;

public interface UserService  {
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public Result login(User user);//登录
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public Result register(User user);//注册
	/**
	 * 获取用户角色
	 * @param userName
	 * @return
	 */
	/**
	 * 退出登录
	 * @param tokenid
	 * @return
	 */
	public Result logout(String tokenid);
	/**
	 * 修改用户信息
	 * @param userName
	 * @return
	 */
	
	public Result changeUserInfo(User user);
	/**
	 * 插入一条评论
	 * @param in
	 * @return
	 */
	/**
	 * 忘记密码
	 * @param user
	 * @return
	 */
	public  Result resetPassword(User user);
	public  Result insertComment(Comment in);
	/**
	 * 
	 * @param in
	 * @return
	 */
	public  Result getComment(Comment in);
	/**
	 * 
	 * @param user
	 * @return 用户信息
	 */
	
	public Result getUserInfo(User user);
	
	public Set<String> getRoles(String userName);
	
	/**
	 * ͨ获取权限
	 * @param userName
	 * @return
	 */
	public Set<String> getPermissions(String userName);
	
	//用户添加收藏
	public Result userAddCollect(Collect collect);
	//用户取消收藏
	public Result userDeleteCollect(Collect collect);
	//获取用户收藏
	public Result getUserCollect(Collect  collect);
	public  Object sss();
	

}
