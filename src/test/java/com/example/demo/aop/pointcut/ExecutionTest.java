package com.example.demo.aop.pointcut;

import com.example.demo.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberService.class.getMethod("hello", String.class); // reflection으로 메소드 정보를 추출한다.
    }

    @Test
    void printMethod() {
        // public abstract java.lang.String com.example.demo.aop.member.MemberService.hello(java.lang.String)
        // execution(*..package..Class.)으로 작성했던 execution이 위의 메소드 반환타입이랑 매칭이 된다.
        log.info("helloMethod={}", helloMethod);
    }
}
