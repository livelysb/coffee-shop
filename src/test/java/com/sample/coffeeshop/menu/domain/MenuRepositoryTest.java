package com.sample.coffeeshop.menu.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void save() {
        // given
        final Menu menu = new Menu("아이스 아메리카노", 4100L);

        // when
        final Menu savedMenu = menuRepository.save(menu);

        // then
        Assertions.assertEquals(menu.getMenuName(), savedMenu.getMenuName());
        Assertions.assertEquals(menu.getMenuPrice(), savedMenu.getMenuPrice());
    }

}