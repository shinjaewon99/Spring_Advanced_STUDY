package com.example.demo.aop.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    // Retry를 하기 위해서는 꼭 최대값을 정해줘야 한다. 그렇지 않으면 셀프 디도스가 발생
    int value() default 3;

}
