package com.simulation.wellmanager.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.simulation.library.domain.Frame;
import com.simulation.wellmanager.service.CriticalValuesCalculator;
import com.simulation.wellmanager.service.FrameService;
import com.simulation.wellmanager.service.FrameValidator;
import com.simulation.wellmanager.service.producer.FrameSender;
import com.simulation.wellmanager.storage.repository.FrameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
@CacheConfig(cacheNames = "frameCache")
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    private final FrameValidator frameValidator;

    private final CriticalValuesCalculator criticalValuesCalculator;

    private final FrameSender frameSender;

    @Override
    @Cacheable(cacheNames = "frames")
    public List<Frame> getAll() {
        return this.frameRepository.getAll();
    }

    @Override
    @Cacheable(cacheNames = "frames", key = "#frameId", unless = "#result == null")
    public Frame getById(final Long frameId) {
        return this.frameRepository.getById(frameId);
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public Frame save(final Frame frame) {
        this.frameValidator.validateFrame(frame);
        this.criticalValuesCalculator.calculateAndSetIsValueCritical(frame);

        final Frame savedFrame = this.frameRepository.save(frame);

        if (Objects.equals(savedFrame.getIsCritical(), Boolean.TRUE)) {
            log.info("Frame [{}] have critical values, it going to be sent to alert service", savedFrame.getId());
            CompletableFuture
                .runAsync(() -> this.frameSender.send(Collections.singleton(savedFrame)));
        }

        return savedFrame;
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public List<Frame> saveAll(final List<Frame> frames) {

        frames.forEach(frame -> {
            this.frameValidator.validateFrame(frame);
            this.criticalValuesCalculator.calculateAndSetIsValueCritical(frame);
        });

        final List<Frame> savedFrames = this.frameRepository.saveAll(frames);

        final Map<Long, Frame> criticalFrames = savedFrames.stream()
            .filter(Frame::getIsCritical)
            .collect(Collectors.toMap(Frame::getId, Function.identity()));

        if (!criticalFrames.isEmpty()) {
            log.info("Frames [{}] have critical values, it going to be sent to alert service", criticalFrames.keySet());
            CompletableFuture
                .runAsync(() -> this.frameSender.send(criticalFrames.values()));
        }

        return savedFrames;
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public Frame update(final Frame frame) {
        this.frameValidator.validateFrame(frame);
        this.criticalValuesCalculator.calculateAndSetIsValueCritical(frame);

        final Frame updatedFrame = this.frameRepository.update(frame);

        if (Objects.equals(updatedFrame.getIsCritical(), Boolean.TRUE)) {
            log.info("Frame [{}] have critical values, it going to be sent to alert service", updatedFrame.getId());
            CompletableFuture
                .runAsync(() -> this.frameSender.send(Collections.singleton(updatedFrame)));
        }

        return updatedFrame;
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "frames", key = "#frameId"),
        @CacheEvict(cacheNames = "frames", allEntries = true)})
    public void deleteById(final Long frameId) {
        this.frameRepository.deleteById(frameId);
    }

}
