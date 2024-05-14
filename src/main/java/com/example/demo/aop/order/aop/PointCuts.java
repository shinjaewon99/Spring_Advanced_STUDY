package com.example.demo.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* com.example.demo.aop.order..*(..))")
    public void allOrder() {
    } // pointcut 시그니처

    // 클래스 이름 패턴이 *Service
    // EX : AaService, BbService 처럼 Service로 끝나는 클래스
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    // allOrder + allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
