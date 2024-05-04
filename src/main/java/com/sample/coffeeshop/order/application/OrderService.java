package com.sample.coffeeshop.order.application;

import com.sample.coffeeshop.common.aop.DistributedLock;
import com.sample.coffeeshop.menu.application.MenuDto;
import com.sample.coffeeshop.menu.application.MenuService;
import com.sample.coffeeshop.order.domain.Order;
import com.sample.coffeeshop.order.domain.OrderRepository;
import com.sample.coffeeshop.order.domain.OrderRequest;
import com.sample.coffeeshop.order.event.OrderCreatedEvent;
import com.sample.coffeeshop.user.application.UserPointService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    MenuService menuService;
    UserPointService userPointService;

    ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    @DistributedLock(key = "'userPointLock'.concat(':').concat(#request.getUserId())")
    public OrderDto createOrder(OrderCreateRequest request) {
        final MenuDto menu = menuService.getMenu(request.getMenuId());
        final Order order = new Order(new OrderRequest(menu, request.getUserId()));
        userPointService.payment(request.getUserId(), order.getOrderPrice());
        final OrderDto orderDto = orderRepository.save(order).toDto();
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(orderDto));
        return orderDto;
    }

}
