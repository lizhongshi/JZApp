package com.dly.app.commons.redis;



import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;


@Component
public class RedisCacheUtil {
	    public static final Long TIME_OUT = 1500l;
	    public static final String USER="User:";
	   
	    @Resource
	    private JedisDataSource redisDataSource;
	    @Resource
	    SerializeUtil serializeUtil;
	static Logger logger = Logger.getLogger(RedisCacheUtil.class);

	 /**
     * 缓存value操作
     * @param key
     * @param value
     * @param 缓存的时间
     * @return
     */
    public   boolean cacheValue(String key, String value, int time) {
    	ShardedJedis jedis = null;
        try {
        	 jedis=redisDataSource.getShardedJedis();
        	jedis.set(key, value);
        	//jedis.expire(key, time);
            logger.info("缓存[" + key+ "], value[" + value + "]");
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", t);
            t.printStackTrace();
        }finally{
        	jedis.close();
        }
        return false;
    }
    
    /**
     * 根据key获取缓存
     * @param key
     * @return
     */
    public   String getValue(String key) {
    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
            String value=jedis.get(key);
            return  value;
    }
    public void sd(String key,long time) {
    	
    }
    /**
     * 根据传入的key和判断value和data是否一致
     * @param key
     * @param data
     * @return
     */
    public   boolean equalValue(String key,String data) {
    	if(key==null||data==null) {
    		return false;
    	}

    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    		 String value=jedis.get(key);
    	        if(data.equals(value)) {
    	        	return  true;
    	        }
    		
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
       
        return  false;
}


    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public void deleteKey(String key) {
    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    		 jedis.del(key);
    		 	logger.info("删除[" + key+ "]");
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
  
   
    	 
    }
    /**
     * 更新key过期时间
     *
     * @param key
     * @return
     */
    public void upKey(String key) {
    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    		jedis.expire(key, 500000);
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
    }
    /**
     * 获取key的剩余过期时间
     * @param key
     * @return
     */
    public long getKeyTime(String key) {
   	 
    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    		return  jedis.ttl(key);
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
	 
	return 0; 
    }
    
    public  void  ss(String groupid,String userId) {
    	ShardedJedis jedis=null;
    	try {
    		jedis=redisDataSource.getShardedJedis();
    		jedis.lpush("comment:"+groupid, userId);
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
    }
    /**
     * 有序集合
     * @param key 
     * @param score  时间戳
     * @param member 值
     */
    public  void  sortedSet(String key,double score,String member ) {
    	ShardedJedis jedis=null;
    	try {
    		
    		jedis=redisDataSource.getShardedJedis();
    		jedis.zadd(key, score, member);
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
    }
   /**
    * 集合
    * @param key
    * @param value
    */
    public  void  set(String key,String members ) {
    	ShardedJedis jedis=null;
    	try {
    		
    		jedis=redisDataSource.getShardedJedis();
    		jedis.sadd(key, members);
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally{
        	jedis.close();
        }
    }
    
    
    
    /**
     * 把对象放入Hash中
     */
    public void hset(String key,String field,Object o){
    	ShardedJedis shardedJedis=null;
    	try {
    		JSONObject jsonObject=new JSONObject();
    	
    		shardedJedis =redisDataSource.getShardedJedis();
    		shardedJedis.hset(key,field, JSONObject.toJSONString(o));
    		logger.info("hash缓存----->  Key:"+key+" field:"+field+" value:"+JSONObject.toJSONString(o));
    	}catch(Exception e) {
    		logger.error("hash缓存失败----->  Key:"+key+" field:"+field+" value:"+o);
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally {
    		shardedJedis.close();
		}
        
    }
    /**
     * 从Hash中获取对象
     */
    public String hget(String key,String field){
    	ShardedJedis shardedJedis=null;
    	 String text=null;
    	try {
    		shardedJedis =redisDataSource.getShardedJedis();
    		 text=shardedJedis.hget(key,field);
    		logger.info("hash获取缓存----->  Key:"+key+" field:"+field);
    		
    	}catch(Exception e) {
    		logger.error("hash获取缓存失败----->  Key:"+key+" field:"+field);
    		 logger.error(e.getMessage());
    		 e.printStackTrace();
    	}finally {
    		shardedJedis.close();
		}
    	
    	 return text;
    }
    /**
     * 从Hash中获取对象,转换成制定类型
     */
    public <T> T hget(String key,String field,Class<T> clazz){
        String text=hget(key, field);
        
        System.out.println(text);
        System.out.println(clazz);
        T result=JSONObject.parseObject(text, clazz);
        return result;
    }
    /**
     * 从Hash中删除对象
     */
    public void hdel(String key,String ... field){
    	ShardedJedis shardedJedis=null;
    	try {
    		 shardedJedis =redisDataSource.getShardedJedis();
    		 Object result=shardedJedis.hdel(key,field);
    		 logger.info("hash 删除缓存 ------>  key:"+key+" field:"+field);
    		
    	}catch(Exception e) {
    		logger.error("hash 删除缓存失败 ------>  key:"+key+" field:"+field);
    		 logger.error(e.getMessage());
    		e.printStackTrace();
    	}finally {
    		shardedJedis.close();
		}
      
    }
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    /**
//     * 会覆盖指定的hashKey
//     * @param key
//     * @param hashKey
//     * @param value
//     */
//    public void hashSet(String key,String hashKey,String value) {
//    	 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//    	 ho.put(key,":"+ hashKey, value); 
//    }
//    /**
//     * 如果指定的hashKey存在不会覆盖
//     * @param key
//     * @param hashKey
//     * @param value
//     * @return
//     */
//    public boolean hashSetIfAbsent(String key,String hashKey,String value) {
//   	 	HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//   	 	return  ho.putIfAbsent(key, hashKey, value); 
//   }
//    /**
//     *缓存map
//     * @param key
//     * @param map
//     * @param value
//     */
//    public boolean hashSetAll(String key,Map<String,String> map,String per ){
//    	String finalKey=key+per;
//    	try {
//    		 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//    		 List<String> keys=new ArrayList<String>();
//    		 List<String> values=new ArrayList<String>();
//    		 for (Object string : map.keySet()) {
//    			 keys.add((String) string);
//    			String value=(String) map.get(string);
//    			values.add(value);
//    		}
//    		 for (int i = 0; i < keys.size(); i++) {
//    			map.remove(keys.get(i));
//    			map.put(":"+keys.get(i), (String) values.get(i));
//    		}
//    		 ho.putAll(finalKey, map);
//    	   	 logger.info("缓存[" + key+ "], map[" + map.toString() + "]");
//    	   	return true;
//             
//        } catch (Throwable t) {
//            logger.error("缓存[" + key + "]失败, map[" + map.toString() + "]", t);
//            return false;
//        }
//   	
//   }
//    /**
//     *获取指定key的hashKeys
//     * @param key
//     * @return
//     */
//    public Set<String>  getHashKeys(String key) {
//      	 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//      Set<String> set=ho.keys(key);
//      return set;
//    }
//    /**
//     * 获取指定key的hashKey
//     * @param key
//     * @return
//     */
//    public String  getHashKey(String key,String hashKey) {
//      	 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//      String value=ho.get(key, hashKey);
//      return value;
//    }
//    /**
//     * 删除指定的hashKey
//     * @param key
//     * @param hashKey
//     * @return
//     */
//    public Long deleteHashKey(String key,String hashKey) {
//   	 HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//   	 	return ho.delete(key, hashKey);
//    }
//    /**
//     * 根据传入的hashKeyList 返回一个valueList
//     * @param key
//     * @param list
//     * @return
//     */
//    public List<String> multiGet(String key,List<String>list){
//    	HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//   	 	return ho.multiGet(key, list);
//    }
//    /**
//     * 
//     * @param key
//     * @return 返回指定的key
//     */
//    public Map<String,String> entries(String key){
//    	HashOperations<String, String, String> ho=redisTemplate.opsForHash();
//   	 	return ho.entries(key);
//    }
    
    

   


    

