package com.sample.coffeeshop.common;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@ActiveProfiles("test")
public class RedisTestContainer {
    private static final String REDIS_DOCKER_IMAGE = "redis";

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE)).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
    }
}
