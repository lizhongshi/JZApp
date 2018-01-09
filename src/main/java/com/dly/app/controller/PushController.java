package com.dly.app.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.service.PushService;

@RestController
@RequestMapping("push")
public class PushController {
	@Resource
	PushService pushService;
//	@PostMapping(produces = "application/json;charset=UTF-8")
//	public  Object push(String body,String title) {
//		return pushService.push(body,title);
//	}
	@GetMapping(produces = "application/json;charset=UTF-8")
	public  Object get(Integer userId) {
		String json=JSONObject.toJSONString(pushService.get(userId));
		return json;
	}

}
