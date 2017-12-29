package com.dly.app.commons.baes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.dly.app.commons.redis.RedisCacheUtil;
import com.dly.app.dao.MediaDAO;
import com.dly.app.dao.UserDAO;
import com.dly.app.service.FastdfsService;
import com.dly.app.service.MediaService;
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
	public MediaService mediaService;
	
	@Resource
	public RedisCacheUtil redisUtil;
	@Resource
	public UserDAO userDao;
	@Resource
	public MediaDAO mediaDao;
	@Resource
	public FastdfsService fastdfsService;
	public Long timeOut=1500l;
	
}
