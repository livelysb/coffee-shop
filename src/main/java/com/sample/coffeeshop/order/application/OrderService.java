package com.sample.coffeeshop.order.application;

import com.sample.coffeeshop.common.LockHandler;
import com.sample.coffeeshop.common.TransactionHandler;
import com.sample.coffeeshop.menu.application.MenuDto;
import com.sample.coffeeshop.menu.application.MenuService;
import com.sample.coffeeshop.order.domain.Order;
import com.sample.coffeeshop.order.domain.OrderRepository;
import com.sample.coffeeshop.order.domain.OrderRequest;
import com.sample.coffeeshop.order.event.OrderCreatedEvent;
import com.sample.coffeeshop.user.application.UserPointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.sample.coffeeshop.user.application.UserPointService.USER_POINT_LOCK_PREFIX;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    MenuService menuService;
    UserPointService userPointService;
    LockHandler lockHandler;
    TransactionHandler transactionHandler;

    ApplicationEventPublisher applicationEventPublisher;



    public OrderDto createOrder(OrderCreateRequest request) {
        return lockHandler.runOnLock(
                USER_POINT_LOCK_PREFIX + request.getUserId(),
                2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(
                        () -> {
                            final MenuDto menu = menuService.getMenu(request.getMenuId());
                            final Order order = new Order(new OrderRequest(menu, request.getUserId()));
                            userPointService.payment(request.getUserId(), order.getOrderPrice());
                            final OrderDto orderDto = orderRepository.save(order).toDto();
                            applicationEventPublisher.publishEvent(new OrderCreatedEvent(orderDto));
                            return orderDto;
                        }));
    }

}
