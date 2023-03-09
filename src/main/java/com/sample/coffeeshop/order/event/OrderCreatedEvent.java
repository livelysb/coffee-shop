package com.sample.coffeeshop.order.event;

import com.sample.coffeeshop.order.application.OrderDto;
import com.sample.coffeeshop.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderCreatedEvent {
    OrderDto order;
}
