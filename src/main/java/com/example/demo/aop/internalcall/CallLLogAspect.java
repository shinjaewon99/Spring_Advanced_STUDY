package com.example.demo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class CallLLogAspect {

    @Before("execution(* com.example.demo.aop.internalcall..*.*(..))")
    public void doLog(Joinpoint joinpoint) {
        log.info("aop={} ", joinpoint.getThis());
    }
}
