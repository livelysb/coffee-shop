package com.sample.coffeeshop.user.application;

import com.sample.coffeeshop.common.CoffeeShopBadRequestException;
import com.sample.coffeeshop.common.CoffeeShopErrors;
import com.sample.coffeeshop.user.domain.PointTransactionRepository;
import com.sample.coffeeshop.user.domain.User;
import com.sample.coffeeshop.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {
    @InjectMocks
    private UserPointService userPointService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PointTransactionRepository pointTransactionRepository;

    @DisplayName("정상적인 유저포인트 차감")
    @Test
    void payment_success() {
        // given
        String testUserId = "livelysb";
        User user = new User(testUserId, 10000L);
        given(userRepository.findByUserId(testUserId)).willReturn(Optional.of(user));

        // when
        userPointService.payment(testUserId, 4100L);

        // then
        Assertions.assertEquals(user.getUserPoint(), 5900L);
    }

    @DisplayName("잔액부족 유저의 결제")
    @Test
    void payment_fail() {
        // given
        String testUserId = "livelysb";
        User user = new User(testUserId, 3000L);
        given(userRepository.findByUserId(testUserId)).willReturn(Optional.of(user));

        // when & then
        CoffeeShopBadRequestException e = Assertions.assertThrows(CoffeeShopBadRequestException.class, () -> {
            userPointService.payment(testUserId, 4100L);
        });
        Assertions.assertEquals(CoffeeShopErrors.INSUFFICIENT_USER_POINT, e.getError());
    }

    @DisplayName("존재하는 회원의 유저포인트 충전")
    @Test
    void charge_exist_user() {
        // given
        String testUserId = "livelysb";
        User user = new User(testUserId, 800L);
        given(userRepository.findByUserId(testUserId)).willReturn(Optional.of(user));

        // when
        userPointService.charge(testUserId, 10000L);

        //then
        Assertions.assertEquals(10800L, user.getUserPoint());
    }

    @DisplayName("존재하지 않는 회원의 유저포인트 충전")
    @Test
    void charge_not_exist_user() {
        // given
        String testUserId = "livelysb";
        given(userRepository.findByUserId(anyString())).willReturn(Optional.empty());

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            userPointService.charge(testUserId, 10000L);
        });
    }
}