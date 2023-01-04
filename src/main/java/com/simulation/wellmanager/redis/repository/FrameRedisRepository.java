package com.simulation.wellmanager.redis.repository;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.simulation.wellmanager.redis.entity.FrameRedisEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FrameRedisRepository {

    private static final String HASH_KEY = "frame";

    private final RedisTemplate<String, FrameRedisEntity> redisTemplate;

    private HashOperations<String, UUID, FrameRedisEntity> hashOperations;

    @PostConstruct
    private void init() {
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public FrameRedisEntity save(final FrameRedisEntity frame) {
        this.hashOperations.put(HASH_KEY, frame.getId(), frame);
        return frame;
    }

    public List<FrameRedisEntity> findAll() {
        return this.hashOperations.values(HASH_KEY);
    }

    public FrameRedisEntity findById(final UUID frameId) {
        return this.hashOperations.get(HASH_KEY, frameId);
    }

    public Long deleteProduct(final UUID frameId) {
        return this.hashOperations.delete(HASH_KEY, frameId);
    }

}
