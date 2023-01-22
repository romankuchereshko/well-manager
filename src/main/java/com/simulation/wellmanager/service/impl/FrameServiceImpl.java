package com.simulation.wellmanager.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.simulation.library.domain.Frame;
import com.simulation.wellmanager.service.FrameService;
import com.simulation.wellmanager.storage.repository.FrameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
@CacheConfig(cacheNames = "frameCache")
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

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
        return this.frameRepository.save(frame);
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public List<Frame> saveAll(final List<Frame> frames) {
        return this.frameRepository.saveAll(frames);
    }

    @Override
    @CacheEvict(cacheNames = "frames", allEntries = true)
    public Frame update(final Frame frame) {
        return this.frameRepository.update(frame);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "frames", key = "#frameId"),
        @CacheEvict(cacheNames = "frames", allEntries = true)})
    public void deleteById(final Long frameId) {
        this.frameRepository.deleteById(frameId);
    }

}
