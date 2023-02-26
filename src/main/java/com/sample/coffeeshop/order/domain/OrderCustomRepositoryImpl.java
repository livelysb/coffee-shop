package com.sample.coffeeshop.order.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sample.coffeeshop.order.application.PopularMenu;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QOrder order = QOrder.order;

    @Override
    public List<PopularMenu> findPopularMenu() {
        LocalDate today = LocalDate.now();
        return jpaQueryFactory
                .select(Projections.bean(PopularMenu.class, order.menuId, order.menuName, order.menuId.count().as("orderedCnt")))
                .from(order)
                .where(
                        new BooleanBuilder()
                                .and(order.orderedAt.goe(today.minusDays(8).atStartOfDay()))
                                .and(order.orderedAt.lt(today.atStartOfDay()))
                )
                .groupBy(order.menuId, order.menuName)
                .orderBy(order.menuId.count().desc())
                .limit(3)
                .fetch();
    }
}
