package com.example.demo.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // com.example.demo.aop.order 패키지와 하위 패키지
    // 접근제어자를 변경하여 여러 곳에서 사용 할 수 있으며, 포인트컷만 모아놓은 클래스로 만들 수 도 있다.
    @Pointcut("execution(* com.example.demo.aop.order..*(..))")
    private void allOrder() {} // pointcut 시그니처

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
