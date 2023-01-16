package com.simulation.wellmanager.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulation.wellmanager.rest.dto.FrameDTO;
import com.simulation.wellmanager.rest.mapper.FrameDTOMapper;
import com.simulation.wellmanager.service.FrameService;
import lombok.AllArgsConstructor;
import oil.station.domain.frame.Frame;

@AllArgsConstructor
@RestController
public class AdminController {

    private final FrameService frameService;

    private final FrameDTOMapper frameDTOMapper;

    @GetMapping(value = "/get-all-frames")
    public ResponseEntity<List<FrameDTO>> getAllFrames() {

        final List<Frame> frames = this.frameService.getAll();

        final List<FrameDTO> frameDTOS = frames.stream()
            .map(this.frameDTOMapper::toFrameDTO)
            .toList();

        return ResponseEntity.ok(frameDTOS);
    }

    @GetMapping(value = "/get-frame-by-id")
    public ResponseEntity<Frame> getFrameById(final Long frameId) {

        final Frame frame = this.frameService.getById(frameId);

        return ResponseEntity.ok(frame);
    }

}
