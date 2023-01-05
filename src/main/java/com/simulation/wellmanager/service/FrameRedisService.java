package com.simulation.wellmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.redis.FrameRedisEntityMapper;
import com.simulation.wellmanager.redis.entity.FrameRedisEntity;
import com.simulation.wellmanager.redis.repository.FrameRedisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oil.station.domain.frame.Frame;

@AllArgsConstructor
@Slf4j
@Component
public class FrameRedisService {

    private final FrameRedisRepository frameRedisRepository;

    private final FrameRedisEntityMapper frameRedisEntityMapper;

    public void saveAll(final List<Frame> frames) {
        final List<UUID> uuids = frames.stream().map(Frame::getId).toList();

        log.info("Frames [{}] with critical values are going to be saved into redis DB", uuids);
        final List<FrameRedisEntity> frameRedisEntities = this.frameRedisRepository.saveAll(frames.stream()
            .map(this.frameRedisEntityMapper::toEntity)
            .toList());

        log.info("Frames [{}] were successfully saved into redis DB",
            frameRedisEntities.stream().map(FrameRedisEntity::getId).toList());
    }

    public List<Frame> getAll() {
        log.info("Going to fetch all Frames from redis DB");
        final List<FrameRedisEntity> frameRedisEntities = this.frameRedisRepository.findAll();

        log.info("Found Frames with ids = [{}] in redis DB",
            frameRedisEntities.stream().map(FrameRedisEntity::getId).toList());
        return frameRedisEntities.stream().map(this.frameRedisEntityMapper::toDomain).toList();
    }

    public Frame getById(final UUID frameId) {
        log.info("Going to find Frame by id = [{}] in redis DB", frameId);
        final FrameRedisEntity frameRedisEntity = this.frameRedisRepository.findById(frameId);

        log.info("Found Frame with id = [{}] in redis DB", frameId);
        return this.frameRedisEntityMapper.toDomain(frameRedisEntity);
    }

}
