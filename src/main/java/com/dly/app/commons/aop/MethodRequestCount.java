package com.dly.app.commons.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.dly.app.dao.TMethodCountMapper;
import com.dly.app.pojo.TMethodCount;

/**
 * 方法请求计数
 * @author 12622
 *
 */
@Component
@Aspect
public class MethodRequestCount {
	static int count=0;
	ArrayList<TMethodCount> arrayList = new ArrayList<TMethodCount>();
	Logger log=Logger.getLogger(MethodRequestCount.class);
	@Resource
	TMethodCountMapper tMethodCountMapper;
	@Around("execution(* com.dly.app.service..*.*(..))")
	public Object count(ProceedingJoinPoint pjp ) {
		 Object result=null;
//		Method method=getMethod(pjp);
//		System.out.println("--------->"+method.getName());
		 log.info("方法执行-------->"+pjp.getSignature().getName());
		 count++;
		 TMethodCount TMethodCount=new TMethodCount();
		 TMethodCount.setMethodName(pjp.getSignature().getName());
		 Date date = new Date();
		 TMethodCount.setCteateTime(date);
		 arrayList.add(TMethodCount);
		 System.out.println("count:"+count);
		 System.out.println(arrayList);
		 if(count%500==0) {
			tMethodCountMapper.inserts(arrayList);
			 arrayList.clear();
		 }
		
		try {
			result=pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public Method getMethod(ProceedingJoinPoint pjp){
        //获取参数的类型
        Object [] args=pjp.getArgs();
        Class [] argTypes=new Class[pjp.getArgs().length];
        System.out.println("////////"+pjp.getSignature().getName());
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
}
