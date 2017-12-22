package com.dly.app.commons.redis;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.activeio.xnet.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.dly.app.commons.baes.Result;
import com.sun.javafx.collections.MappingChange.Map;

import redis.clients.jedis.ShardedJedis;

@Aspect
@Order(value = 1)
@Component("redisLogServiceInterceptor")
public class RedisLogServiceInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLogServiceInterceptor.class);
    @Resource 
    private  RedisCacheUtil redis;
 

    /**
     * 
     * 
     * @Title: execute
     * @Description: 切入点业务逻辑
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(RedisLogService)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws ServiceException {
        Object result = null;

        try {
            Method method = getMethod(proceedingJoinPoint);

            // 获取注解对象
            RedisLogService redisLogService = method.getAnnotation(RedisLogService.class);

            // 判断是否使用缓存
            boolean useRedis = redisLogService.use();

            if (useRedis) {

                // 使用redis
               

                // 判断当前操作
                switch (redisLogService.cacheOperation()) {

                case FIND:

                    result = executeDefault(redisLogService,  proceedingJoinPoint, method);

                    break;
//                case UPDATE:
//
//                    result = executeUpdate(redisLogService,  proceedingJoinPoint);
//
//                    break;
//                case INSERT:
//
//                    result = executeInsert(redisLogService, proceedingJoinPoint);
//
//                    break;
                default:

                    result = proceedingJoinPoint.proceed();

                    break;
                }
            } else {

                result = proceedingJoinPoint.proceed();
            }

        } catch (ServiceException e) {
            throw e;
        } catch (Throwable e) {
            //throw new ServiceException(new Result<Object>("500", e.getMessage()), e);
        }
        return result;
    }


    /**
     * 
     * @Title: getMethod
     * @Description: 获取被拦截方法对象
     * @param joinPoint
     * @return
     */
    protected Method getMethod(JoinPoint joinPoint) throws Exception {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();

        return method;
    }
    
    /**
     * 
     * @Title: executeDefault
     * @Description: 默认操作的执行
     * @param redisLogService
     * @param result
     * @param operations
     * @param proceedingJoinPoint
     * @param method
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private Object executeDefault(RedisLogService redisLogService, 
            ProceedingJoinPoint proceedingJoinPoint, Method method) throws Throwable {

        Object result = null;

        Object[] args = proceedingJoinPoint.getArgs();

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();

        String[] paraNameArr = u.getParameterNames(method);

        // 获取key的后缀的参数名
        String key = redisLogService.key();

        if (!"".equals(key)) {
            // 使用SPEL进行key的解析
            ExpressionParser parser = new SpelExpressionParser();

            // SPEL上下文
            StandardEvaluationContext context = new StandardEvaluationContext();

            // 把方法参数放入SPEL上下文中
            for (int i = 0; i < paraNameArr.length; i++) {

                context.setVariable(paraNameArr[i], args[i]);
            }

            Object object = parser.parseExpression(key).getValue(context);

            if (null != object) {

                if (object instanceof Map<?, ?>) {
                	Map m=(Map)object;
                    key = m.toString();

                } else if (object instanceof Collection<?>) {

                    Collection<Object> collection = (Collection<Object>) object;

                    StringBuffer stringBuffer = new StringBuffer();

                    for (Object o : collection) {

                        stringBuffer.append(o.toString());
                    }

                    key = stringBuffer.toString();
                } else {

                    key = object.toString();
                }
            }
        }

        String className = proceedingJoinPoint.getTarget().getClass().getName();

        if (className.indexOf(".") >= 0) {

            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }

        String methodName = method.getName();

        String[] group = redisLogService.group();

        if (null != group && group.length > 0) {

            if (!"".equals(key)) {

                key = group[0] + ":" + className + ":" + methodName + ":" + key;
            } else {

                key = group[0] + ":" + className + ":" + methodName;
            }
        } else {

            if (!"".equals(key)) {

                key = "group" + ":" + className + ":" + methodName + ":" + key;
            } else {

                key = "group" + ":" + className + ":" + methodName;
            }
        }

        result = redis.getValue(key);

        // 如果缓存没有数据则更新缓存
        if (result == null) {

            result = proceedingJoinPoint.proceed();

            int expire = redisLogService.expire();

            // 更新缓存
            if (expire > 0) {

                //operations.set(key, result, expire, TimeUnit.SECONDS);
                redis.cacheValue(key, result.toString(), expire);
         
            } else {

            	redis.cacheValue(key, result.toString(), expire);
            }
        }

        return result;
    }
}