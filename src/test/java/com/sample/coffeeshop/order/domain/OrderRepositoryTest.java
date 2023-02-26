package com.sample.coffeeshop.order.domain;

import com.sample.coffeeshop.menu.application.MenuDto;
import com.sample.coffeeshop.user.domain.User;
import com.sample.coffeeshop.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("livelysb"));
    }

    @Test
    void save() {
        // given
        final Order order = new Order(new OrderRequest(new MenuDto(1L, "아이스 아메리카노", 4200L), "livelysb"));

        // when
        final Order savedOrder = orderRepository.save(order);

        // then
        Assertions.assertEquals(savedOrder.getMenuId(), 1L);
        Assertions.assertEquals(savedOrder.getMenuName(), "아이스 아메리카노");
        Assertions.assertEquals(savedOrder.getOrderPrice(), 4200L);
        Assertions.assertTrue(savedOrder.getOrderedAt().isBefore(LocalDateTime.now()));
    }
}