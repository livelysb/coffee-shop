package com.sample.coffeeshop.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    List<PointTransaction> findAllByUserUserId(String userId);
}
