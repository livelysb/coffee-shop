package com.sample.coffeeshop.order.domain;

import com.sample.coffeeshop.order.application.OrderDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String userId;

    private Long menuId;

    private String menuName;

    private Long orderPrice;

    private LocalDateTime orderedAt;

    public Order(OrderRequest orderRequest) {
        this.userId = orderRequest.getUserId();
        this.menuId = orderRequest.getMenu().getMenuId();
        this.menuName = orderRequest.getMenu().getMenuName();
        this.orderPrice = orderRequest.getMenu().getMenuPrice();
        this.orderedAt = LocalDateTime.now();
    }

    public OrderDto toDto() {
        return new OrderDto(orderId, userId, menuId, menuName, orderPrice, orderedAt);
    }
}
