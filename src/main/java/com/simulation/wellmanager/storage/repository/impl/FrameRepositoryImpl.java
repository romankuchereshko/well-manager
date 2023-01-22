package com.simulation.wellmanager.storage.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.wellmanager.domain.exception.FrameException;
import com.simulation.wellmanager.storage.entity.FrameEntity;
import com.simulation.wellmanager.storage.jpa.FrameEntityRepository;
import com.simulation.wellmanager.storage.mapper.FrameEntityMapper;
import com.simulation.wellmanager.storage.repository.FrameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.simulation.library.domain.Frame;

@Slf4j
@AllArgsConstructor
@Component
public class FrameRepositoryImpl implements FrameRepository {

    private final FrameEntityRepository frameEntityRepository;

    private final FrameEntityMapper frameEntityMapper;

    private final EntityManager entityManager;

    @Override
    public List<Frame> getAll() {
        log.info("Starting fetching all frames from DB");

        final List<Frame> frames = this.frameEntityRepository.findAll()
            .stream()
            .map(this.frameEntityMapper::toDomain)
            .toList();

        log.info("[{}] frames where successfully fetched from DB", frames.size());
        return frames;
    }

    @Override
    public Frame getById(final Long frameId) {
        log.info("Staring fetching frame [{}] from DB", frameId);

        return this.frameEntityRepository.findById(frameId)
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
    public Frame save(final Frame frame) {
        final FrameEntity savedFrame = this.frameEntityRepository.save(this.frameEntityMapper.toEntity(frame));
        log.info("Frame [{}] was successfully saved into DB", savedFrame.getId());

        return this.frameEntityMapper.toDomain(savedFrame);
    }

    @Override
    public List<Frame> saveAll(final List<Frame> frames) {
        final List<FrameEntity> frameEntities = frames
            .stream()
            .map(this.frameEntityMapper::toEntity)
            .toList();

        final List<FrameEntity> savedFrames = this.frameEntityRepository.saveAll(frameEntities);

        log.info("Frames [{}] were successfully saved into DB",
            savedFrames.stream().map(FrameEntity::getId).toList());

        return savedFrames.stream()
            .map(this.frameEntityMapper::toDomain)
            .toList();
    }

    @Transactional
    @Override
    public Frame update(final Frame frame) {
        final Long frameId = frame.getId();

        return this.frameEntityRepository.findById(frameId)
            .stream()
            .findFirst()
            .map(frameEntity -> {

                log.info("Frame [{}] was fetched from DB and will be updated", frameEntity.getId());
                final FrameEntity updatedFrame = this.frameEntityRepository
                    .save(this.frameEntityMapper.toEntity(frame));
                log.info("Frame [{}] was successfully updated in DB", frameEntity.getId());
                this.entityManager.flush();
                log.info("Updated frame entity: [[{}].", updatedFrame);
                this.entityManager.refresh(updatedFrame);
                log.info("Updated frame entity after refresh: [[{}].", updatedFrame);
                return this.frameEntityMapper.toDomain(updatedFrame);
            })
            .orElseThrow(() -> {
                final String message = String.format("There is no frame %s in DB", frameId);
                log.info(message);

                throw new FrameException(message);
            });
    }

    @Override
    public void deleteById(final Long frameId) {
        try {
            log.info("Starting deleting frame [{}] from DB", frameId);
            this.frameEntityRepository.deleteById(frameId);
            log.info("Frame [{}] was successfully deleted from DB", frameId);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new FrameException(String.format("There is no frame with id %s exists in DB", frameId));
        }
    }
}
