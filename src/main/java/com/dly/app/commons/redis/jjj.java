//package com.dly.app.commons.redis;
//
//import java.lang.reflect.Method;
//import java.util.Collection;
//import java.util.concurrent.TimeUnit;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//
//
//import com.sun.javafx.collections.MappingChange.Map;
//
//public class jjj {
//	/**
//     * 
//     * @Title: executeDefault
//     * @Description: 默认操作的执行
//     * @param redisLogService
//     * @param result
//     * @param operations
//     * @param proceedingJoinPoint
//     * @param method
//     * @throws Throwable
//     */
//    @SuppressWarnings("unchecked")
//    private Object executeDefault(RedisLogService redisLogService, ValueOperations<String, Object> operations,
//            ProceedingJoinPoint proceedingJoinPoint, Method method) throws Throwable {
//
//        Object result = null;
//
//        Object[] args = proceedingJoinPoint.getArgs();
//
//        // 获取被拦截方法参数名列表(使用Spring支持类库)
//        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//
//        String[] paraNameArr = u.getParameterNames(method);
//
//        // 获取key的后缀的参数名
//        String key = redisLogService.key();
//
//        if (StringUtils.isNotBlank(key)) {
//            // 使用SPEL进行key的解析
//            ExpressionParser parser = new SpelExpressionParser();
//
//            // SPEL上下文
//            StandardEvaluationContext context = new StandardEvaluationContext();
//
//            // 把方法参数放入SPEL上下文中
//            for (int i = 0; i < paraNameArr.length; i++) {
//
//                context.setVariable(paraNameArr[i], args[i]);
//            }
//
//            Object object = parser.parseExpression(key).getValue(context);
//
//            if (null != object) {
//
//                if (object instanceof Map<?, ?>) {
//
//                    key = GzdtlStringUtil.transMapToString((Map<String, Object>) object);
//
//                } else if (object instanceof Collection<?>) {
//
//                    Collection<Object> collection = (Collection<Object>) object;
//
//                    StringBuffer stringBuffer = new StringBuffer();
//
//                    for (Object o : collection) {
//
//                        stringBuffer.append(o.toString());
//                    }
//
//                    key = stringBuffer.toString();
//                } else {
//
//                    key = object.toString();
//                }
//            }
//        }
//
//        String className = proceedingJoinPoint.getTarget().getClass().getName();
//
//        if (className.indexOf(".") >= 0) {
//
//            className = className.substring(className.lastIndexOf(".") + 1, className.length());
//        }
//
//        String methodName = method.getName();
//
//        String[] group = redisLogService.group();
//
//        if (null != group && group.length > 0) {
//
//            if (StringUtils.isNotBlank(key)) {
//
//                key = group[0] + ":" + className + ":" + methodName + ":" + key;
//            } else {
//
//                key = group[0] + ":" + className + ":" + methodName;
//            }
//        } else {
//
//            if (StringUtils.isNotBlank(key)) {
//
//                key = "group" + ":" + className + ":" + methodName + ":" + key;
//            } else {
//
//                key = "group" + ":" + className + ":" + methodName;
//            }
//        }
//
//        result = operations.get(key);
//
//        // 如果缓存没有数据则更新缓存
//        if (result == null) {
//
//            result = proceedingJoinPoint.proceed();
//
//            int expire = redisLogService.expire();
//
//            // 更新缓存
//            if (expire > 0) {
//
//                operations.set(key, result, expire, TimeUnit.SECONDS);
//            } else {
//
//                operations.set(key, result);
//            }
//        }
//
//        return result;
//    }
//    /**
//     * 
//     * @Title: executeDefault
//     * @Description: 默认操作的执行
//     * @param redisLogService
//     * @param result
//     * @param operations
//     * @param proceedingJoinPoint
//     * @param method
//     * @throws Throwable
//     */
//    @SuppressWarnings("unchecked")
//    private Object executeDefault(RedisLogService redisLogService, ValueOperations<String, Object> operations,
//            ProceedingJoinPoint proceedingJoinPoint, Method method) throws Throwable {
//
//        Object result = null;
//
//        Object[] args = proceedingJoinPoint.getArgs();
//
//        // 获取被拦截方法参数名列表(使用Spring支持类库)
//        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//
//        String[] paraNameArr = u.getParameterNames(method);
//
//        // 获取key的后缀的参数名
//        String key = redisLogService.key();
//
//        if (StringUtils.isNotBlank(key)) {
//            // 使用SPEL进行key的解析
//            ExpressionParser parser = new SpelExpressionParser();
//
//            // SPEL上下文
//            StandardEvaluationContext context = new StandardEvaluationContext();
//
//            // 把方法参数放入SPEL上下文中
//            for (int i = 0; i < paraNameArr.length; i++) {
//
//                context.setVariable(paraNameArr[i], args[i]);
//            }
//
//            Object object = parser.parseExpression(key).getValue(context);
//
//            if (null != object) {
//
//                if (object instanceof Map<?, ?>) {
//
//                    key = GzdtlStringUtil.transMapToString((Map<String, Object>) object);
//
//                } else if (object instanceof Collection<?>) {
//
//                    Collection<Object> collection = (Collection<Object>) object;
//
//                    StringBuffer stringBuffer = new StringBuffer();
//
//                    for (Object o : collection) {
//
//                        stringBuffer.append(o.toString());
//                    }
//
//                    key = stringBuffer.toString();
//                } else {
//
//                    key = object.toString();
//                }
//            }
//        }
//
//        String className = proceedingJoinPoint.getTarget().getClass().getName();
//
//        if (className.indexOf(".") >= 0) {
//
//            className = className.substring(className.lastIndexOf(".") + 1, className.length());
//        }
//
//        String methodName = method.getName();
//
//        String[] group = redisLogService.group();
//
//        if (null != group && group.length > 0) {
//
//            if (StringUtils.isNotBlank(key)) {
//
//                key = group[0] + ":" + className + ":" + methodName + ":" + key;
//            } else {
//
//                key = group[0] + ":" + className + ":" + methodName;
//            }
//        } else {
//
//            if (StringUtils.isNotBlank(key)) {
//
//                key = "group" + ":" + className + ":" + methodName + ":" + key;
//            } else {
//
//                key = "group" + ":" + className + ":" + methodName;
//            }
//        }
//
//        result = operations.get(key);
//
//        // 如果缓存没有数据则更新缓存
//        if (result == null) {
//
//            result = proceedingJoinPoint.proceed();
//
//            int expire = redisLogService.expire();
//
//            // 更新缓存
//            if (expire > 0) {
//
//                operations.set(key, result, expire, TimeUnit.SECONDS);
//            } else {
//
//                operations.set(key, result);
//            }
//        }
//
//        return result;
//    }
//
//
//}
