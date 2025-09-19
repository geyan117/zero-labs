package com.mars.zero.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author geyan
 * @date 2025/9/7
 */
@Configuration
@MapperScan("com.mars.zero.server.mapper")
public class ZeroMybatisConfig {
}
