package com.example.demo.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {

    // Trace라는 어노테이션이 있는곳은 적용 됨
    @Before("@annotation(com.example.demo.aop.exam.annotation.Trace)")
    public void doTrace(Joinpoint joinpoint) {
        Object args = joinpoint.getThis();
        log.info("[trace] {} args={}", joinpoint.getThis(), args);
    }
}
