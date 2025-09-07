package com.mars.zero.server.redis;

import com.mars.zero.server.ZeroServerApplication;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author geyan
 * @date 2025/9/7
 */
@SpringBootTest(classes = ZeroServerApplication.class)
public class ZeroRedisTest {


    @Test
    public void testHello() {
        User user = getUser(1);
        Assertions.assertEquals("zhangsan", user.getName());
    }


    @Cacheable(value = "user", key = "#id")
    public User getUser(int id) {
        return User.builder()
                .name("zhangsan")
                .age(18)
                .id(1)
                .build();
    }


    @Data
    @Builder
    public static class User {
        private String name;
        private int age;
        private int id;
    }
}
