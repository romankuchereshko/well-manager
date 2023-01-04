package com.simulation.wellmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.simulation.wellmanager.redis.entity.FrameRedisEntity;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, FrameRedisEntity> redisTemplate() {
        final RedisTemplate<String, FrameRedisEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(this.jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setEnableTransactionSupport(true);
        return template;
    }

}
