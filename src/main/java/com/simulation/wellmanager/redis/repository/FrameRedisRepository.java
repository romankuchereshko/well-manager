package com.simulation.wellmanager.redis.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public FrameRedisEntity save(final FrameRedisEntity frame) {
        log.debug("Start saving frame with uuid = [{}] to redis DB", frame.getId());

        this.redisTemplate.opsForHash().put(HASH_KEY, frame.getId(), frame);

        log.debug("Frame with uuid = [{}] was successfully saved to redis DB", frame.getId());

        return frame;
    }

    public List<FrameRedisEntity> saveAll(final List<FrameRedisEntity> frame) {
        final List<UUID> uuids = frame.stream().map(FrameRedisEntity::getId).toList();

        log.debug("Start saving frames with uuids = [{}] to redis DB", uuids);

        final Map<UUID, FrameRedisEntity> uuidFrameMap = frame.stream()
            .collect(Collectors.toMap(FrameRedisEntity::getId, Function.identity()));
        this.redisTemplate.opsForHash().putAll(HASH_KEY, uuidFrameMap);

        log.debug("Frames with uuids = [{}] were successfully saved to redis DB", uuids);

        return frame;
    }

    public List<FrameRedisEntity> findAll() {

        log.debug("Start fetching all frames from redis DB");

        final List<FrameRedisEntity> frames = this.redisTemplate.opsForHash().values(HASH_KEY)
            .stream().map(FrameRedisEntity.class::cast)
            .toList();

        log.debug("Frames with uuids = [{}] were successfully fetched from redis DB",
            frames.stream().map(FrameRedisEntity::getId).toList());

        return frames;
    }

    public FrameRedisEntity findById(final UUID frameId) {

        log.debug("Start fetching frame by uuid = [{}] from redis DB", frameId);

        FrameRedisEntity frameRedisEntity = (FrameRedisEntity) this.redisTemplate.opsForHash().get(HASH_KEY, frameId);

        log.debug("Frame was successfully fetched by uuid = [{}] from redis DB", frameId);

        return frameRedisEntity;
    }

    public Long deleteFrame(final UUID frameId) {

        log.debug("Start deleting frame by uuid = [{}] from redis DB", frameId);

        final Long delete = this.redisTemplate.opsForHash().delete(HASH_KEY, frameId);

        log.debug("Frame by uuid = [{}] was successfully deleted from redis DB", frameId);

        return delete;
    }

}
