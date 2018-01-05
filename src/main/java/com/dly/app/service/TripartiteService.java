package com.dly.app.service;

import com.dly.app.commons.baes.Result;
import com.dly.app.pojo.User;

public interface TripartiteService {
	public Result login(User user);
	public Result bind(User user);

}
