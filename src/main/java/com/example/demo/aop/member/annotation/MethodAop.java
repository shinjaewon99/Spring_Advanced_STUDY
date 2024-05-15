package com.example.demo.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Method에 붙일 수 있는 어노테이션
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAop {
    String value();
}
