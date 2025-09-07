package com.mars.zero.infra.redis.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geyan
 * @date 2025/9/7
 */
@AutoConfiguration
@EnableCaching
public class ZeroCacheConfiguration {

    /**
     * 配置 Jedis 连接工厂
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("127.0.0.1"); // Redis 服务器地址
        config.setPort(6379);            // Redis 端口
        config.setDatabase(0); // 指定数据库索引 (0-15)
        // 创建 Jedis 连接工厂
        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        // 显示启动连接工厂
        factory.start();
        return factory;
    }

    /**
     * 配置 RedisTemplate，提供更丰富的操作 API
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory()); // 使用上面的 Jedis 连接工厂

        // 设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 初始化配置
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 配置 RedisCacheManager，为 Spring Cache 提供 Redis 实现
     */
    @Bean
    public RedisCacheManager cacheManager() {
        // 定义默认的缓存配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // 默认缓存10分钟
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues(); // 不缓存 null 值

        // 为特定的缓存区域 (cacheNames) 定制过期时间
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put("users", defaultConfig.entryTtl(Duration.ofHours(1)));      // 用户缓存1小时
//        configMap.put("products", defaultConfig.entryTtl(Duration.ofMinutes(30))); // 商品缓存30分钟

        return RedisCacheManager.builder(redisConnectionFactory()) // 使用 Jedis 连接工厂
                .cacheDefaults(defaultConfig)                      // 默认配置
                .withInitialCacheConfigurations(configMap)         // 特定配置
                .build();
    }

}
