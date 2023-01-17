package com.simulation.wellmanager.service.impl;

import java.util.List;

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
    @Cacheable(cacheNames = "frames", key = "#frameId", unless = "#result == null")
    public Frame getById(final Long frameId) {
        log.info("Trying to find frame [{}] in DB", frameId);

        return this.frameRepository.findById(frameId)
            .stream()
            .findFirst()
            .map(frameEntity -> {

                log.info("Frame [{}] was fetched from DB", frameEntity.getId());
                return this.frameEntityMapper.toDomain(frameEntity);
            })
            .orElseThrow(() -> {
                final String message = String.format("There is no frame %s in DB", frameId);
                log.info(message);

                throw new FrameException(message);
            });
    }

    @Override
    @CacheEvict(cacheNames = "customers", allEntries = true)
    public boolean save(final Frame frame) {

        final FrameEntity savedFrame = this.frameRepository.save(this.frameEntityMapper.toEntity(frame));

        log.info("Frame [{}] was successfully saved into DB", savedFrame.getId());

        return true;
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public boolean saveAll(final List<Frame> frames) {
        final List<FrameEntity> newFrames = frames
            .stream()
            .map(this.frameEntityMapper::toEntity)
            .toList();

        final List<FrameEntity> frameEntities = this.frameRepository.saveAll(newFrames);

        log.info("Frames [{}] were successfully saved into DB",
            frameEntities.stream().map(FrameEntity::getId).toList());

        return true;
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public Frame update(final Frame frame) {
        final Long frameId = frame.getId();

        return this.frameRepository.findById(frameId)
            .stream()
            .findFirst()
            .map(frameEntity -> {

                log.info("Frame [{}] was fetched from DB and will be updated", frameEntity.getId());
                final FrameEntity savedFrame = this.frameRepository.save(this.frameEntityMapper.toEntity(frame));

                log.info("Frame [{}] was successfully updated in DB", frameEntity.getId());
                return this.frameEntityMapper.toDomain(savedFrame);
            })
            .orElseThrow(() -> {
                final String message = String.format("There is no frame %s in DB", frameId);
                log.info(message);

                throw new FrameException(message);
            });
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "frames", key = "#frameId"),
        @CacheEvict(cacheNames = "frames", allEntries = true)})
    public void deleteById(final Long frameId) {
        log.info("Trying to delete frame [{}] from DB", frameId);

        this.frameRepository.deleteById(frameId);

        log.info("Frame [{}] was successfully deleted from DB", frameId);
    }


}
