package com.example.demo.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 구조를 변경 (스프링에서 가장 권장)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal(); // 외부의 InternalService를 호출하게 되므로, 프록시 객체를 호출하는 것이다.
    }
}
