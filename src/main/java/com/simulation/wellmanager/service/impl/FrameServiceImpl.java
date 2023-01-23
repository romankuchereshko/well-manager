package com.simulation.wellmanager.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.simulation.library.domain.Frame;
import com.simulation.library.producer.FrameSender;
import com.simulation.wellmanager.service.CriticalValuesCalculator;
import com.simulation.wellmanager.service.FrameService;
import com.simulation.wellmanager.service.FrameValidator;
import com.simulation.wellmanager.storage.repository.FrameRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "frameCache")
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    private final FrameValidator frameValidator;

    private final CriticalValuesCalculator criticalValuesCalculator;

    private final FrameSender frameSender;

    @Value("${spring.kafka.topic.alert-topic}")
    private String topic;

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

        this.frameSender.send(Collections.singleton(frame), this.topic);

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

        this.frameSender.send(savedFrames, this.topic);

        return savedFrames;
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public Frame update(final Frame frame) {
        this.frameValidator.validateFrame(frame);
        this.criticalValuesCalculator.calculateAndSetIsValueCritical(frame);

        final Frame updateFrame = this.frameRepository.update(frame);

        this.frameSender.send(Collections.singleton(updateFrame), this.topic);

        return updateFrame;
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "frames", key = "#frameId"),
        @CacheEvict(cacheNames = "frames", allEntries = true)})
    public void deleteById(final Long frameId) {
        this.frameRepository.deleteById(frameId);
    }

}
