package com.example.demo.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /**
     * 어드바이스의 종류
     *
     * @Around : @Around에서 모든 기능을 할 수 있음
     * @Before
     * @AfterReturning
     * @AfterThrowing
     * @After : finally 로직
     */

    @Around("com.example.demo.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("com.example.demo.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(Joinpoint joinPoint) {
        log.info("[before] {}", joinPoint.getThis()); // joinPoint를 실행하기 직전까지 적용
    }

    @AfterReturning(value = "com.example.demo.aop.order.aop.PointCuts.orderAndService()", returning = "result")
    public void doReturn(Joinpoint joinpoint, Object result) {
        log.info("[return] {} return {}", joinpoint.getThis(), result);
    }

    @AfterThrowing(value = "com.example.demo.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(Joinpoint joinpoint, Exception ex) {
        log.info("[ex] {} message = {}", joinpoint.getThis(), ex);
    }

    @After(value = "com.example.demo.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(Joinpoint joinpoint) {
        log.info("[after] {}", joinpoint.getThis());
    }
}
