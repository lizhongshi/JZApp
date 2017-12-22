package com.dly.app.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.service.SmsService;
/**
 * 短信发送接口
 * @author 12622
 *
 */
@RestController
@RequestMapping(value="sms")
public class SmsController extends SuperClass {
	@Resource
	private SmsService smsService;
	private static Logger log = Logger.getLogger(SmsController.class);
	@GetMapping(value="shortMessage",produces = "application/json;charset=UTF-8")
	public Object  shortMessage( String phone){
			log.info("发送短信请求参数++:"+phone);
		 Result result= smsService.sendSms(phone);
		 String json=JSONObject.toJSONString(result);
		 log.info("发送短信返回参数++:"+json);
		
			return json;
	}
	
}
