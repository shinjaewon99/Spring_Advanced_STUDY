package com.example.demo.aop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external() {
        callServiceV0.external();
    }

    @Test
    void internal() {
        callServiceV0.internal();
    }

    /**
     * 테스트를 출력하게 되면, external에는 aop가 적용이 되는데,
     * external() 메소드에서 호출한 internal() 메소드는 aop 적용이 되지 않는다.
     *
     * 이유 : 결과적으로 자기 자신의 내부 메소드를 호출하는 것임으로 (this) 이러한 내부 호출은 프록시를 거치지 않습니다.
     */
}