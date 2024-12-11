package com.sample.coffeeshop.common.aop;

import com.sample.coffeeshop.common.CoffeeShopException;
import com.sample.coffeeshop.common.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.sample.coffeeshop.common.CoffeeShopErrors.LOCK_ACQUISITION_FAILED;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {
    private final RedissonClient redissonClient;

    @Around("@annotation(com.sample.coffeeshop.common.aop.DistributedLock)")
    public Object runOnLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DistributedLock annotation = signature.getMethod().getAnnotation(DistributedLock.class);

        String REDISSON_KEY_PREFIX = "RLOCK_";
        String key = (String) CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.key());

        RLock lock = redissonClient.getLock(REDISSON_KEY_PREFIX + key);
        try {
            boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!available) {
                throw new CoffeeShopException(LOCK_ACQUISITION_FAILED);
            }
            log.info("get lock success. key={}", key);
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            log.info("unlock success. key={}", key);
        }

    }
}
