package com.mars.zero.server.service;


import com.mars.zero.server.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author geyan
 * @date 2025/9/7
 */
@Service
public class ZeroService {

    @Cacheable(value = "user", key = "#id")
    public User getUser(int id) {
        return User.builder()
                .name("zhangsan")
                .age(18)
                .id(1)
                .build();
    }
}
