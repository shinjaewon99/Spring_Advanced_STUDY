package com.example.demo.aop.pointcut;


import com.example.demo.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={} ", memberService.getClass());
        memberService.hello("helloA");
    }

    /**
     * this와 target은 실무에서 많이 사용하지는 않지만, 이론적인 개념은 한번 짚고 넘어가는게 좋다.
     * this와 target은 단독으로 쓰이는것보다 파라미터와 함께 같이 사용
     */
    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // this든 target이든 부모타입을 허용 한다.
        @Around("this(com.example.demo.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.example.demo.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // JDK동적 프록시로 동작시키게 되면 this는 프록시를 대상으로 하므로 Impl클래스를 알지 못한다.
        // CGLIB으로 동작시키게 되면 Impl 클래스도 log로 출력된다.
        @Around("this(com.example.demo.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.example.demo.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
