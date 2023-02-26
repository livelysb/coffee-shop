package com.sample.coffeeshop.order.application;

import com.sample.coffeeshop.menu.application.MenuDto;
import com.sample.coffeeshop.menu.application.MenuService;
import com.sample.coffeeshop.order.domain.Order;
import com.sample.coffeeshop.order.domain.OrderRepository;
import com.sample.coffeeshop.order.domain.OrderRequest;
import com.sample.coffeeshop.user.application.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final MenuService menuService;

    private final UserPointService userPointService;

    @Transactional
    public OrderDto createOrder(OrderCreateRequest request) {
        final MenuDto menu = menuService.getMenu(request.getMenuId());
        final Order order = orderRepository.save(new Order(new OrderRequest(menu, request.getUserId())));
        userPointService.payment(request.getUserId(), order.getOrderPrice());
        return order.toDto();
    }

    @Transactional(readOnly = true)
    public List<PopularMenu> getPopularMenu() {
        return orderRepository.findPopularMenu();
    }
}
