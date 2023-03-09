package com.sample.coffeeshop.user.application;

import com.sample.coffeeshop.common.CoffeeShopBadRequestException;
import com.sample.coffeeshop.common.LockHandler;
import com.sample.coffeeshop.user.domain.PointTransaction;
import com.sample.coffeeshop.user.domain.PointTransactionRepository;
import com.sample.coffeeshop.user.domain.User;
import com.sample.coffeeshop.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sample.coffeeshop.common.CoffeeShopErrors.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserPointService {
    private final UserRepository userRepository;
    private final PointTransactionRepository pointTransactionRepository;

    private final LockHandler lockHandler;

    public static String USER_POINT_LOCK_PREFIX = "USER_";

    @Transactional
    public void payment(String userId, Long usingPoint) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CoffeeShopBadRequestException(USER_NOT_FOUND));
        user.usePoint(usingPoint);
        pointTransactionRepository.save(PointTransaction.createByPayment(user, usingPoint));
    }

    @Transactional
    public void charge(String userId, Long chargingPoint) {
        lockHandler.runOnLock(
                USER_POINT_LOCK_PREFIX + userId,
                2000L,
                1000L,
                () -> {
                    User user = userRepository.findByUserId(userId).orElse(new User(userId));
                    user.chargePoint(chargingPoint);
                    pointTransactionRepository.save(PointTransaction.createByCharge(user, chargingPoint));
                    return null;
                });

    }
}
