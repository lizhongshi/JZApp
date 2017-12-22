package app;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Tess {
	static	ShardedJedisPool  jedis=null;
	static{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/test.xml");
		  jedis =ctx.getBean(ShardedJedisPool.class);
	}
	@Test
	public void t1() {
		ShardedJedis j= jedis.getResource();
		j.set("aaa", "aadd");
	}
	@Test
	public void t2() {
		ShardedJedis j= jedis.getResource();
		System.out.println(j.get("aaa"));
	}

}
