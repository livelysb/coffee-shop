package com.sample.coffeeshop.user.domain;

import com.sample.coffeeshop.common.CoffeeShopBadRequestException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sample.coffeeshop.common.CoffeeShopErrors.INSUFFICIENT_USER_POINT;

@Entity
@Table(name = "`user`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String userId;

    private Long userPoint;

    public User(String userId) {
        this.userId = userId;
        userPoint = 0L;
    }

    public User(String userId, Long userPoint) {
        this.userId = userId;
        this.userPoint = userPoint;
    }

    public void usePoint(Long usingPoint) {
        if (userPoint < usingPoint) {
            throw new CoffeeShopBadRequestException(INSUFFICIENT_USER_POINT);
        }
        userPoint -= usingPoint;
    }

    public void chargePoint(Long chargingPoint) {
        userPoint += chargingPoint;
    }
}
