package com.sample.coffeeshop.order.domain;

import com.sample.coffeeshop.menu.application.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequest {
    private MenuDto menu;
    private String userId;
}
