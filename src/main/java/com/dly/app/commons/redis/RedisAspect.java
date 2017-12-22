package com.dly.app.commons.redis;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.ShardedJedis;
@Aspect
public class RedisAspect {
	@Resource
	RedisCacheUtil cacheUtil;
	private Boolean isCache;
	
	 public Boolean getIsCache() {
		return isCache;
	}
	public void setIsCache(Boolean isCache) {
		this.isCache = isCache;
	}



	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Around("@annotation(com.dly.app.commons.redis.Cacheable)")
	 public Object  cache(ProceedingJoinPoint pjp ) { 
		 Object result=null;
		 if(!isCache) {//判断是否开启缓存 在spring配置文件配置是否开启
			 try {
				result=pjp.proceed();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return result;
		 }
		 try {
			Method method=getMethod(pjp);
			//获取注解对象
			Cacheable cacheable=method.getAnnotation(Cacheable.class); 
			//获取参数值
			String fieldKeys="";
			System.out.println("fieldKey---->"+cacheable.fieldKey());
			if(cacheable.fieldKey().length>0) {
				for (int i = 0; i <cacheable.fieldKey().length; i++) {
					System.out.println("循环----->"+cacheable.fieldKey()[i]);
					String fieldKey =cacheable.fieldKey()[i]+":"+parseKey(cacheable.fieldKey()[i],method,pjp.getArgs());
					fieldKeys+=fieldKey;
				}
			}else {
				fieldKeys=cacheable.key();
			}
			System.out.println("fieldKeys"+fieldKeys);
			 //获取方法的返回类型,让缓存可以返回正确的类型
            Class<?> returnType=((MethodSignature)pjp.getSignature()).getReturnType();
            result= cacheUtil.hget(cacheable.key(), fieldKeys,returnType);
            if(result==null){
                try {
                	//执行目标方法
                    result=pjp.proceed();
                    Assert.notNull(fieldKeys);
                    cacheUtil.hset(cacheable.key(),fieldKeys, result);
                    cacheUtil.set("cacheSet", cacheable.key());
                    
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
	 }catch (Throwable e) {
         e.printStackTrace();
     }
		 return result;
		 
	 }
	 
	 
	 
	/**
	 * 定义清除缓存逻辑
	 * @param pjp
	 * @return
	 */
     @Around(value="@annotation(CacheEvict)")
     public Object evict(ProceedingJoinPoint pjp ){
    	 
    	 Object retVal=null;
    	 Method method=getMethod(pjp);
    	 CacheEvict cacheEvict=method.getAnnotation(CacheEvict.class); 
    	 String fieldKeys="";
    		if(cacheEvict.fieldKey().length>0) {
	    	 for (int i = 0; i <cacheEvict.fieldKey().length; i++) {
					System.out.println("循环----->"+cacheEvict.fieldKey()[i]);
					String fieldKey =cacheEvict.fieldKey()[i]+":"+parseKey(cacheEvict.fieldKey()[i],method,pjp.getArgs());
					fieldKeys+=fieldKey;
				}
    		}else {
    			fieldKeys=cacheEvict.key();
    		}
		
			System.out.println(fieldKeys);
			cacheUtil.hdel(cacheEvict.key(), fieldKeys);
			try {
				////执行目标方法并获取原方法的返回参数
				 retVal = pjp.proceed();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return retVal;
     }
     
     /**
      *  获取被拦截方法对象
      *  
      *  MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
      *    而缓存的注解在实现类的方法上
      *  所以应该使用反射获取当前对象的方法对象
      */
     public Method getMethod(ProceedingJoinPoint pjp){
         //获取参数的类型
         Object [] args=pjp.getArgs();
         Class [] argTypes=new Class[pjp.getArgs().length];
         for(int i=0;i<args.length;i++){
             argTypes[i]=args[i].getClass();
         }
         Method method=null;
         try {
             method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),argTypes);
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (SecurityException e) {
             e.printStackTrace();
         }
         return method;
         
     }
     /**
      *    获取缓存的key 
      *    key 定义在注解上，支持SPEL表达式
      * @param pjp
      * @return
      */
     private String parseKey(String key,Method method,Object [] args){
         
         
         //获取被拦截方法参数名列表(使用Spring支持类库)
         LocalVariableTableParameterNameDiscoverer u =   
             new LocalVariableTableParameterNameDiscoverer();  
         String [] paraNameArr=u.getParameterNames(method);
         
         //使用SPEL进行key的解析
         ExpressionParser parser = new SpelExpressionParser(); 
         //SPEL上下文
         StandardEvaluationContext context = new StandardEvaluationContext();
         //把方法参数放入SPEL上下文中
         for(int i=0;i<paraNameArr.length;i++){
             context.setVariable(paraNameArr[i], args[i]);
         }
         return parser.parseExpression(key).getValue(context,String.class);
     }
 
	 
	 
	 

}
