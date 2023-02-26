package com.sample.coffeeshop.order.domain;

import com.sample.coffeeshop.order.application.PopularMenu;

import java.util.List;

public interface OrderCustomRepository {
    List<PopularMenu> findPopularMenu();
}
