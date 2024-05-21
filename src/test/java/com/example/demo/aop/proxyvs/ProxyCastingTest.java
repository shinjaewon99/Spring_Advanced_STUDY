package com.example.demo.aop.proxyvs;

import com.example.demo.aop.member.MemberService;
import com.example.demo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시 (default)

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy(); // 인터페이스를 구현해서 만듬

        // JDK 동적 프록시는 인터페이스를 아는것이지 구현체를 아는것이 아님
        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        Assertions
                .assertThrows(ClassCastException.class, () -> {
                MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
                });

    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy(); // 인터페이스를 구현해서 만듬

        // CGLIB 프록시는 인터페이스도 알고 구현체도 아는 것
        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

    /**
     * 정리
     * JDK 동적 프록시는 대상 객체인 MemberServiceImpl로 캐스팅 할 수 없다
     * CGLIB 프록시는 대상 객체인 MemberServiceImpl로 캐스팅 할 수 있음
     */
}
