package com.example.demo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    // 생성자 대신 setter로 주입 받는 이유는 생성자를 사용하게 되면,
    // CallServiceV1이 생성되지 않았는데 메서드를 사용하므로, 순환참조 문제가 생김
    @Autowired
    public void setCallServiceV1(final CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메소드 호출 (프록시)
    }

    public void internal() {
        log.info("call internal");
    }
}
