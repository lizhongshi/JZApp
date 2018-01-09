package com.dly.app.service;

import com.dly.app.commons.baes.Result;

public interface PushService {
	public Result push(String body,String title) ;
	public Result get(Integer userId) ;

}
