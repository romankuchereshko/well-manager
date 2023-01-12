package com.simulation.wellmanager.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.simulation.wellmanager.domain.exception.FrameException;
import com.simulation.wellmanager.jpa.FrameEntityMapper;
import com.simulation.wellmanager.jpa.entity.FrameEntity;
import com.simulation.wellmanager.jpa.repository.FrameRepository;
import com.simulation.wellmanager.service.FrameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oil.station.domain.frame.Frame;

@AllArgsConstructor
@Slf4j
@Component
@CacheConfig(cacheNames = "frameCache")
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    private final FrameEntityMapper frameEntityMapper;

    @Override
    @Cacheable(cacheNames = "frames")
    public List<Frame> getAll() {
        log.info("Starting fetching all frames from DB");
        final List<FrameEntity> frames = this.frameRepository.findAll();

        log.info("[{}] frames where successfully fetched from DB", frames.size());
        return frames.stream().map(this.frameEntityMapper::toDomain).toList();
    }

    @Override
    @Cacheable(cacheNames = "customer", key = "#id", unless = "#result == null")
    public Frame getById(UUID frameId) {
        log.info("Trying to find frame [{}] in DB", frameId);
        Optional<FrameEntity> optFrameEntity = this.frameRepository.findById(frameId);

        if (optFrameEntity.isEmpty()) {
            final String message = String.format("There is no frame %s in DB", frameId);
            log.info(message);
            throw new FrameException(message);
        }

        log.info("Frame [{}] was fetched from DB", frameId);
        return this.frameEntityMapper.toDomain(optFrameEntity.get());
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public List<Frame> saveAll(final List<Frame> frames) {
        final List<UUID> uuids = frames.stream().map(Frame::getId).toList();

        log.info("Frames [{}] are going to be saved into DB", uuids);
        final List<FrameEntity> frameEntities = this.frameRepository.saveAll(frames.stream()
            .map(this.frameEntityMapper::toEntity)
            .toList());

        log.info("Frames [{}] were successfully saved into DB",
            frameEntities.stream().map(FrameEntity::getId).toList());

        return frames;
    }

    @Override
    @CacheEvict(cacheNames = "customers", allEntries = true)
    public Frame save(Frame frame) {
        log.info("Frame [{}] is going to be saved into DB", frame.getId());
        final FrameEntity frameEntity = frameRepository.save(this.frameEntityMapper.toEntity(frame));

        log.info("Frame [{}] was successfully saved into DB", frameEntity.getId());

        return this.frameEntityMapper.toDomain(frameEntity);
    }

    @Override
    @CacheEvict(cacheNames = "customers", allEntries = true)
    public Frame update(Frame frame) {
        final UUID frameId = frame.getId();

        log.info("Trying to find frame [{}] in DB", frameId);
        Optional<FrameEntity> optFrameEntity = this.frameRepository.findById(frameId);

        if (optFrameEntity.isEmpty()) {
            final String message = String.format("There is no frame %s in DB", frameId);
            log.info(message);
            throw new FrameException(message);
        }

        log.info("Frame [{}] was fetched from DB and will be updated", frameId);
        final FrameEntity savedFrame = this.frameRepository.save(this.frameEntityMapper.toEntity(frame));

        log.info("Frame [{}] was successfully updated in DB", frameId);
        return this.frameEntityMapper.toDomain(savedFrame);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "customer", key = "#id"),
        @CacheEvict(cacheNames = "customers", allEntries = true)})
    public void deleteById(UUID frameId) {
        log.info("Trying to delete frame [{}] from DB", frameId);
        this.frameRepository.deleteById(frameId);
    }


}
