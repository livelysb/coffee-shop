package com.sample.coffeeshop.order.application;

import com.sample.coffeeshop.order.domain.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PopularMenuService {
    OrderRepository orderRepository;

    final static String POPULAR_MENU = "POPULAR_MENU";


    @Cacheable(POPULAR_MENU)
    @Transactional(readOnly = true)
    public List<PopularMenu> getPopularMenuList() {
        return orderRepository.findPopularMenu();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(POPULAR_MENU)
    public void evictPopularMenuCache() {
    }
}
