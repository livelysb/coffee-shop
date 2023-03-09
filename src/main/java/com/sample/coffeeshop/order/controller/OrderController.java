package com.sample.coffeeshop.order.controller;

import com.sample.coffeeshop.order.application.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderController {

    OrderService orderService;
    PopularMenuService popularMenuService;

    @Operation(summary = "커피를 주문한다.")
    @PostMapping("/order")
    public OrderDto createOrder(@RequestBody @Valid OrderCreateRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @Operation(summary = "7일 간의 인기메뉴 TOP3를 조회한다.")
    @GetMapping("/popular-menu")
    public List<PopularMenu> getPopularMenu() {
        return popularMenuService.getPopularMenuList();
    }
}
