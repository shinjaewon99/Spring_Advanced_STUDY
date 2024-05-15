package com.example.demo.aop.member;

import com.example.demo.aop.member.annotation.ClassAop;
import com.example.demo.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(final String value) {
        return "ok";
    }

    public String internal(final String value) {
        return "ok";
    }
}
