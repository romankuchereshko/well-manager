package com.simulation.wellmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.jpa.FrameEntityMapper;
import com.simulation.wellmanager.jpa.entity.FrameEntity;
import com.simulation.wellmanager.jpa.repository.FrameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oil.station.domain.frame.Frame;

@AllArgsConstructor
@Slf4j
@Component
public class FramePostgresService {

    private final FrameRepository frameRepository;

    private final FrameEntityMapper frameEntityMapper;

    public void saveAll(final List<Frame> frames) {
        final List<UUID> uuids = frames.stream().map(Frame::getId).toList();

        log.info("Frames [{}] with critical values are going to be saved into postgres DB", uuids);
        final List<FrameEntity> frameEntities = this.frameRepository.saveAll(frames.stream()
            .map(this.frameEntityMapper::toEntity)
            .toList());

        log.info("Frames [{}] were successfully saved into postgres DB",
            frameEntities.stream().map(FrameEntity::getId).toList());
    }

}
