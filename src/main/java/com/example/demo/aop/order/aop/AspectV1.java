package com.example.demo.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
// @Aspect는 표식이지 컴포넌트 스캔의 대상이 아니므로 @Bean (@Component) 을 사용하여 Bean으로 등록 해줘야 한다
public class AspectV1 {

    // AOP를 걸 패키지 경로를 적어준다.
    // ProceedingJoinPoint를 파라미터로 넘기는것이 규칙
    @Around("execution(* com.example.demo.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed(); // target 이 호출
    }
    /**
     * OrderRepository, OrderService 클래스는 모두 AOP적용의 대상이 된다.
     */
}
