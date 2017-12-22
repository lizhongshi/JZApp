package com.dly.app.commons.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class JedisDataSource {
	 private static final Logger log = LoggerFactory.getLogger(JedisDataSource.class);
	@Resource
	private ShardedJedisPool shardedJedisPool;
	/**
	 * 
	 * @return jedis实例
	 */
	public ShardedJedis getShardedJedis() {
		 ShardedJedis shardJedis = null;
		 try {
			 shardJedis=shardedJedisPool.getResource();
			 return shardJedis;
		 }catch(Exception e) {
			 log.error(e.getMessage());
			 if (null != shardJedis)
	            shardJedis.close();
		 }
		return null;
	}
	/**
	 * 关闭一个jedis
	 * @param shardedJedis 
	 */
	    public void returnResource(ShardedJedis shardedJedis) {
	        shardedJedis.close();
	    }

}
