package com.simulation.wellmanager.service;

import java.util.List;

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
public class FrameService {

    private final FrameRepository frameRepository;

    private final FrameEntityMapper frameEntityMapper;

    public void saveFrames(final List<Frame> frames) {
        log.info("Frames with critical values are going to be saved into DB");
        final List<FrameEntity> frameEntities = this.frameRepository.saveAll(frames.stream()
            .map(this.frameEntityMapper::toEntity)
            .toList());

        log.info("Frames [{}] were successfully saved into DB",
            frameEntities.stream().map(FrameEntity::getId).toList());
    }

}
