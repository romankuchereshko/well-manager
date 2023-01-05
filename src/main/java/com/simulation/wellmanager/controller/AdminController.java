package com.simulation.wellmanager.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulation.wellmanager.service.FrameRedisService;
import lombok.AllArgsConstructor;
import oil.station.domain.frame.Frame;

@AllArgsConstructor
@RestController
public class AdminController {

    private final FrameRedisService frameRedisService;

    @GetMapping(value = "/get-all-frames")
    public ResponseEntity<List<Frame>> getAllFrames() {

        final List<Frame> frames = this.frameRedisService.getAll();

        return ResponseEntity.ok(frames);
    }

    @GetMapping(value = "/get-frame-by-id")
    public ResponseEntity<Frame> getFrameById(final UUID frameId) {

        final Frame frame = this.frameRedisService.getById(frameId);

        return ResponseEntity.ok(frame);
    }

}
