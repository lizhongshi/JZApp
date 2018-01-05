package app;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Tess {
	
	static{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		 
	}
	public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("你好啊");
    }
	
	@Test
	public void t2() {
		 JPushClient jpushClient = new JPushClient("fe3bbe7e6bab3fb5549ea52f", "f89775890fc82a08efbcaeb3", null, ClientConfig.getInstance());

		    // For push, all you need do is to build PushPayload object.
		    PushPayload payload = buildPushObject_all_all_alert();

		    try {
		        try {
					PushResult result = jpushClient.sendPush(payload);
				} catch (cn.jpush.api.common.resp.APIRequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
			
		    } catch (APIConnectionException e) {
		        // Connection error, should retry later

		    }
	}

}
