package com.simulation.wellmanager.redis.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.simulation.wellmanager.redis.entity.FrameRedisEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("Start saving frame with uuid = [{}] to redis DB", frame.getId());

        this.hashOperations.put(HASH_KEY, frame.getId(), frame);

        log.debug("Frame with uuid = [{}] was successfully saved to redis DB", frame.getId());

        return frame;
    }

    public List<FrameRedisEntity> saveAll(final List<FrameRedisEntity> frame) {
        final List<UUID> uuids = frame.stream().map(FrameRedisEntity::getId).toList();

        log.debug("Start saving frames with uuids = [{}] to redis DB", uuids);

        final Map<UUID, FrameRedisEntity> uuidFrameMap = frame.stream()
            .collect(Collectors.toMap(FrameRedisEntity::getId, Function.identity()));
        this.hashOperations.putAll(HASH_KEY, uuidFrameMap);

        log.debug("Frames with uuids = [{}] were successfully saved to redis DB", uuids);

        return frame;
    }

    public List<FrameRedisEntity> findAll() {

        log.debug("Start fetching all frames from redis DB");

        final List<FrameRedisEntity> frameRedisEntities = this.hashOperations.values(HASH_KEY);

        log.debug("Frames with uuids = [{}] were successfully fetched from redis DB",
            frameRedisEntities.stream().map(FrameRedisEntity::getId).toList());

        return frameRedisEntities;
    }

    public FrameRedisEntity findById(final UUID frameId) {

        log.debug("Start fetching frame by uuid = [{}] from redis DB", frameId);

        FrameRedisEntity frameRedisEntity = this.hashOperations.get(HASH_KEY, frameId);

        log.debug("Frame was successfully fetched by uuid = [{}] from redis DB", frameId);

        return frameRedisEntity;
    }

    public Long deleteProduct(final UUID frameId) {

        log.debug("Start deleting frame by uuid = [{}] from redis DB", frameId);

        final Long delete = this.hashOperations.delete(HASH_KEY, frameId);

        log.debug("Frame by uuid = [{}] was successfully deleted from redis DB", frameId);

        return delete;
    }

}
