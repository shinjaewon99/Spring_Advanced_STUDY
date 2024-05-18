package com.example.demo.aop.pointcut;

import com.example.demo.aop.member.MemberService;
import com.example.demo.aop.member.annotation.ClassAop;
import com.example.demo.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy ={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* com.example.demo.aop.member ..*.*(..))")
        private void allMember() {

        }

        // allMember() 로 경로를 1차로 묶어준다.
        @Around("allMember()")
        public Object logArg1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];// helloA라는 값이 반환, 배열의 값을 꺼낸다는것이 좋은 방법은 아님
            log.info("[logArgs1] {}, args={}", joinPoint.getSignature(), arg1);

            return joinPoint.proceed();
        }

        //args로 하게 되면 파라미터로 받을 수 있게된다.
        @Around("allMember() && args(arg,..)")
        public Object logArg2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, args={}", joinPoint.getSignature(), arg);

            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        // this와 target은 파라미터로 클래스를 직접 지정할 수 있다.
        /**
         * this와 target의 차이점은
         * target은 실제 대상 구현체 EX : memberServiceImpl
         * this는 실제 대상 구현체 프록시 객체 EX : memberServiceImpl$cglib$
         *
         * */
        @Before("allMember() && this(obj)")
        public void thisArgs(Joinpoint joinpoint, MemberService obj) {
            log.info("[this] {}, obj={}", joinpoint.getThis(), obj.getClass());
        }

        @Before("allMember() && target(obj)")
        public void targetArgs(Joinpoint joinpoint, MemberService obj) {
            log.info("[target] {}, obj={}", joinpoint.getThis(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(Joinpoint joinpoint, ClassAop annotation) {
            log.info("[@target] {}, obj={}", joinpoint.getThis(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(Joinpoint joinpoint, ClassAop annotation) {
            log.info("[@within] {}, obj={}", joinpoint.getThis(), annotation);
        }

        // 어노테이션의 들어있는 값을 꺼낼 수 있음
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(Joinpoint joinpoint, MethodAop annotation) {
            log.info("[@annotation] {}, annotationValue={}", joinpoint.getThis(), annotation.value());
        }
    }
}
