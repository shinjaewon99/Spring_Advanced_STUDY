package com.example.demo.aop;

import com.example.demo.aop.order.OrderRepository;
import com.example.demo.aop.order.OrderService;
import com.example.demo.aop.order.aop.AspectV4PointCut;
import com.example.demo.aop.order.aop.AspectV5Order;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
/*@Import(AspectV1.class)*/
/*@Import(AspectV2.class)*/
//@Import(AspectV3.class)
/*@Import(AspectV4PointCut.class)*/
@Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class}) // 클래스별로 따로 등록, @Bean으로 등록하여 한번에 처리해도 됨
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopTest() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {

        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
