package com.example.demo.aop.pointcut;

import com.example.demo.aop.member.MemberService;
import com.example.demo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberService.class.getMethod("hello", String.class); // reflection으로 메소드 정보를 추출한다.
    }

    /**
     * within 문법은 타입이 정확하게 일치해야 매칭이 된다.
     */
    @Test
    void withinMatch() {
        // execution에서 타입 부분만 가져와서 매칭 하는 것
        pointcut.setExpression("within(com.example.demo.aop.member.MemberServiceImpl)");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안됨")
    void withinSuperTypeFalse() {
        // execution에서 타입 부분만 가져와서 매칭 하는 것
        pointcut.setExpression("within(com.example.demo.aop.member.MemberService)");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입기반, 인터페이스 선정 가능")
    void executionSuperTypeTrue() {
        // execution에서 타입 부분만 가져와서 매칭 하는 것
        pointcut.setExpression("execution(* com.example.demo.aop.member.MemberService.*(..))");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
