package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class T {
	static RedisTemplate<String, String> redisTemplate;
	static {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		redisTemplate=ctx.getBean(RedisTemplate.class);
	}
	@Test
	public void t1() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		System.out.println( ho.get("zhangsan","age"));
	}
	@Test
	public void t2() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		 ho.put("zhangsan", "age1", "123");
		 List<String> list=new ArrayList();
		 list= ho.multiGet("", list);
	}
	@Test
	public void t4() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		 ho.put("zhangsan", "age", "12");
	}
	@Test
	public void t3() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		System.out.println( ho.delete("zhangsan","s"));
	}
	@Test
	public void t6() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		 Map map= ho.entries("user:14");
		System.out.println( map);
	}
	@Test
	public void t7() {
		 ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
		RedisOperations<String, String> redisOps=valueOps.getOperations();
		System.out.println(redisOps); 
	}
	@Test
	public void t8() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		 Map<String,String> map=new HashMap<String,String>();
		 map.put("name", "张三");
		 map.put("age", "12");
		 List l=new ArrayList();
		 List l2=new ArrayList();
		 for (Object string : map.keySet()) {
			 System.out.println("++++");
			 l.add(string);
			String value=(String) map.get(string);
			l2.add(value);
		}
		 for (int i = 0; i < l.size(); i++) {
			map.remove(l.get(i));
			map.put(":"+l.get(i), (String) l2.get(i));
		}
		 ho.putAll("user:14", map);
	}
	@Test
	public void t9() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		
		System.out.println( ho.keys("user:14"));
	}
	@Test
	public void t10() {
		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
		 ValueOperations<String, String> vo=redisTemplate.opsForValue();
		 
	}
	

}
