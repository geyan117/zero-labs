package com.mars.zero.server.controller;

import com.mars.zero.server.model.User;
import com.mars.zero.server.service.ZeroService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author geyan
 * @date 2025/9/7
 */
@RestController
@RequestMapping("/zero")
@RequiredArgsConstructor
public class ZeroController {

    private final ZeroService zeroService;

    @GetMapping("/hello")
    public String hello() {
        return "hello zero-labs";
    }

    @GetMapping("/get")
    public String getUser() {
        User user = zeroService.getUser(1);
        return user.toString();
    }


}
