package com.mars.zero.server.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author geyan
 * @date 2025/9/7
 */
@Data
@Builder
public class User {

    private String name;
    private int age;
    private int id;
}
