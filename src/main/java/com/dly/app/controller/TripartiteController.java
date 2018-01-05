package com.dly.app.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dly.app.pojo.User;
import com.dly.app.service.TripartiteService;

/**
 * 第三方登录
 * @author 12622
 *
 */
@RestController
@RequestMapping(value="tripartite")
public class TripartiteController {
	@Resource
	TripartiteService tripartiteService;
	@PostMapping(value="login",produces = "application/json;charset=UTF-8")
	public  Object login(@RequestBody User user) {
		
		return tripartiteService.login(user);
	}
	@PostMapping(value="bind",produces = "application/json;charset=UTF-8")
	public  Object bind(@RequestBody User user) {
		
		return tripartiteService.bind(user);
	}
}
