package com.sample.coffeeshop.order.controller;

import com.sample.coffeeshop.order.application.OrderCreateRequest;
import com.sample.coffeeshop.order.application.OrderDto;
import com.sample.coffeeshop.order.application.OrderService;
import com.sample.coffeeshop.order.application.PopularMenu;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "커피를 주문한다.")
    @PostMapping("/order")
    public OrderDto createOrder(@RequestBody @Valid OrderCreateRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @Operation(summary = "7일 간의 인기메뉴 TOP3를 조회한다.")
    @GetMapping("/popular-menu")
    public List<PopularMenu> getPopularMenu() {
        return orderService.getPopularMenu();
    }
}
