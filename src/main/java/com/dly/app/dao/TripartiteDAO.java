package com.dly.app.dao;

import com.dly.app.pojo.User;

public interface TripartiteDAO {
	public User  getUserByQq(String qq);
	public User  getUserByWeixin(String weixin);
	//public User  getUserByWeixin(String weixin);
	public int bind(User user);
	public User  getUserByPhone(String phone);
	public  int  register(User user);
}
