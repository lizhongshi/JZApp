package com.dly.app.commons.jpush;

import org.springframework.stereotype.Component;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class JpushUtil {
	
	public  boolean push(String text) throws APIConnectionException, APIRequestException {
		 JPushClient jpushClient = new JPushClient(masterSecret, appkey, null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = PushPayload.alertAll(text);

		   
					PushResult result = jpushClient.sendPush(payload);
					System.out.println("返回"+result);
			
		return true;
	}
	private String appkey;
	private String masterSecret;
	
	
	
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}
	
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
}
