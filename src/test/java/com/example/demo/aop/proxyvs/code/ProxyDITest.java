package com.example.demo.aop.proxyvs.code;

import com.example.demo.aop.member.MemberService;
import com.example.demo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 프록시
@SpringBootTest // 스프링 2.0 버전 이상 부터는 default가 CGLIB 프록시 이므로, 잘 동작 하게된다.
@Import(ProxyDITest.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    // JDK 동적 프록시에 구체 클래스를 주입하면 에러가 발생, JDK 동적 프록시를 사용할 경우에는 인터페이스를 주입해야 한다.
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class ={}", memberService.getClass());
        log.info("memberServiceImpl class ={}", memberServiceImpl.getClass());

        memberServiceImpl.hello("hello");
    }
}
