package com.sample.coffeeshop.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
}
