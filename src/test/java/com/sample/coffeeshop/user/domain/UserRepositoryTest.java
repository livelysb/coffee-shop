package com.sample.coffeeshop.user.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // given
        final User user = new User("livelysb");

        // when
        final User savedUser = userRepository.save(user);

        // then
        Assertions.assertEquals(savedUser.getUserId(), "livelysb");
        Assertions.assertEquals(savedUser.getUserPoint(), 0L);
    }

}