package com.dly.app.commons.baes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.dao.MediaDAO;
import com.dly.app.dao.UserDAO;
import com.dly.app.service.FastdfsServce;
import com.dly.app.service.MediaServce;
import com.dly.app.service.UserService;
/**
 * 超类
 * @author 12622
 *
 */
public class SuperClass {
	@Resource
	public UserService userService;
	@Resource
	public MediaServce mediaServce;
	
	@Resource
	public RedisCacheUtil redisUtil;
	@Resource
	public UserDAO userDao;
	@Resource
	public MediaDAO mediaDao;
	@Resource
	public FastdfsServce fastdfsServce;
	public Long timeOut=1500l;
	
}
