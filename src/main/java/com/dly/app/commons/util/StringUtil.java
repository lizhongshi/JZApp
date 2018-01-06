package com.dly.app.commons.util;

public class StringUtil {
	public static  boolean strIsEmpty(String str) {
		if(null==str||"".equals(str.trim())) {
			
			return true;
		}else {
			return false;
		}
	}
	
	public static  boolean strIsNotEmpty(String str) {
	
		if(null!=str&&!"".equals(str.trim())) {
			
			return true;
		}else {
			return false;
		}
	}
	public static  boolean strIsNotEmpty(Integer str) {
		if(null!=str&&"".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	

}
