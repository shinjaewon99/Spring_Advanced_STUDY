package com.example.demo.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderItem(final String itemId) {
        log.info("[orderService] 실행");

        orderRepository.save(itemId);
    }
}
