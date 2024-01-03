package com.sample.coffeeshop.order.controller;

import com.sample.coffeeshop.common.CoffeeShopErrors;
import com.sample.coffeeshop.common.CoffeeShopException;
import com.sample.coffeeshop.order.application.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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

    @Operation(summary = "에러발생을 위한 테스트용 API")
    @PostMapping("/failed-order")
    public OrderDto failedOrder() {
        log.error("[!] [CRITICAL_ERROR] ORDER_CREATE_FAILED: 주문 실패 - 테스트");
        throw new CoffeeShopException(CoffeeShopErrors.INTERNAL_SERVER_ERROR);
    }
}
