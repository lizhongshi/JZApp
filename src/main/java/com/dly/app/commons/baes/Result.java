package com.dly.app.commons.baes;



import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 接口返回封装类
 * @author 12622
 *
 */
public class Result {
	
	private String struts;//成功或者失败
	private String code;//状态码
	private String msg;//成功的消息
	private String error;//失败的消息
	private Object data;//返回的数据
	
	public Result(String struts, String code, String msg, String error, Object data) {
		this.struts = struts;
		this.code = code;
		this.msg = msg;
		this.error = error;
		this.data = data;
	}
	public Result(String struts, String code, String msg, String error) {
		this.struts = struts;
		this.code = code;
		this.msg = msg;
		this.error = error;
	
	}
	public Result() {
		
	}
	public String getStruts() {
		return struts;
	}
	public void setStruts(String struts) {
		this.struts = struts;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}




}
