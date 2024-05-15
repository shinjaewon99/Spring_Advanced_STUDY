package com.example.demo.aop.pointcut;

import com.example.demo.aop.member.MemberService;
import com.example.demo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
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


    // 가장 정확하게 매칭
    @Test
    void exactMatch() {
        /**
         * 매칭 조건
         * 접근제어자 : public
         * 반환타입 : String
         * 선언타입 : 패지키 클래스 경로
         * 메서드 이름 : hello
         * 파라미터 : (String)
         * 예외 : 생략
         */
        pointcut.setExpression("execution(public String com.example.demo.aop.member.MemberServiceImpl.hello(String))");

        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 가장 많이 생략한 매칭
    @Test
    void allMatch() {
        /**
         * 매칭 조건
         * 접근제어자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메서드 이름 : *
         * 파라미터 : (..)
         * 예외 : 생략
         * '*'은 아무 값이 들어와도 된다는 뜻
         */
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* com.example.demo.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* com.example.demo.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        // 패키지 경로를 작성시 '.' 과 '..' 의 차이를 이해 해야함, '.'은 정확한 위치의 패키지 '..' 은 해당 위치의 패키지지와 그 하위 패키지도 포함
        pointcut.setExpression("execution(* com.example.demo.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* com.example.demo.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* com.example.demo.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
