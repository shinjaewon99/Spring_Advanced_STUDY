package com.example.demo.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* com.example.demo.aop.order..*(..))")
    private void allOrder() {
    } // pointcut 시그니처

    // 클래스 이름 패턴이 *Service
    // EX : AaService, BbService 처럼 Service로 끝나는 클래스
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {
    }


    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // com.example.demo.aop.order 패키지의 하위 패키지이면서, 클래스 이름 패턴이 *Service인 경우
    @Around("allOrder() && allService()") // pointcut을 조합 할 수 있다. (&& (AND), || (OR), ! (NOT))
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }
        // finally는 리소스를 정리할때 많이 사용
        finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }

    }
}
