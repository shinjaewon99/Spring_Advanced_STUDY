package com.example.demo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext; // ApplicationContext를 사용해도 되지만 과분하다.
//    public CallServiceV2(final ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

    // 스프링 빈 생성 시점이 아니라, 실제 객체를 사용하는 시점으로 지연할 수 있다.
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(final ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class); // Bean으로 등록된 클래스를 꺼낼 수 있음
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); // 외부 메소드 호출 (프록시)
    }

    public void internal() {
        log.info("call internal");
    }
}
