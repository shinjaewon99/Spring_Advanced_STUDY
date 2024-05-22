package com.example.demo.aop.proxyvs.code;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class ProxyDIAspect {

    @Before("execution(* com.example.demo.aop..*.*(..))")
    public void doTrace(Joinpoint joinpoint) {
        log.info("proxyDIAdvice = {}", joinpoint.getThis());
    }
}
