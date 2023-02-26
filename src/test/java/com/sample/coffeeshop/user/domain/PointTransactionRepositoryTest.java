package com.sample.coffeeshop.user.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PointTransactionRepositoryTest {
    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveWithNewUser() {
        // given
        final PointTransaction pointTransaction = PointTransaction.createByCharge(new User("livelysb"), 10000L);

        // when
        final PointTransaction savedPointTransaction = pointTransactionRepository.save(pointTransaction);

        // then
        Assertions.assertEquals(savedPointTransaction.getUser().getUserId(), "livelysb");
        Assertions.assertEquals(savedPointTransaction.getPoint(), 10000L);
        Assertions.assertTrue(savedPointTransaction.getTransactedAt().isBefore(LocalDateTime.now()));
    }

    @Test
    void saveWithExistingUser() {
        // given
        User user = userRepository.save(new User("livelysb"));
        final PointTransaction pointTransaction = PointTransaction.createByCharge(user, 10000L);

        // when
        final PointTransaction savedPointTransaction = pointTransactionRepository.save(pointTransaction);

        // then
        Assertions.assertEquals(savedPointTransaction.getUser().getUserId(), "livelysb");
        Assertions.assertEquals(savedPointTransaction.getPoint(), 10000L);
        Assertions.assertTrue(savedPointTransaction.getTransactedAt().isBefore(LocalDateTime.now()));
    }
}
