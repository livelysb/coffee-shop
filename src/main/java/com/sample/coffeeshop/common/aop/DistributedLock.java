package com.sample.coffeeshop.common.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    /**
     * <a href="https://docs.spring.io/spring-framework/reference/core/expressions.html">Spring Expression Language (SpEL) expression</a> used for making the lock key.
     */
    String key();
}
