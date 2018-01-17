package com.dly.app.commons.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.dly.app.dao.TMethodCountMapper;
import com.dly.app.pojo.TMethodCount;

public class LogAspect {
	
}
