package com.dly.app.commons.redis;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Cacheable {
	 String key() ;
     String[] fieldKey() ;
     int expireTime() default 3600;
}
