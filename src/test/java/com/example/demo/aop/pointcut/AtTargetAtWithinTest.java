package com.example.demo.aop.pointcut;

import com.example.demo.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    Child child;

    @Test
    void success() {
        log.info("child proxy ={} ", child.getClass());
        child.childMethod(); // 부모, 자식 모두 있는 메소드
        child.parentMethod(); // 부모 클래스에만 있는 메소드
    }


    static class Parent {
        public void parentMethod() {
        }

        ; // 부모에만 있는 메소드
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod() {
        }
    }
}
