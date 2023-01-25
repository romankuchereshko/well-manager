package com.simulation.wellmanager.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simulation.library.domain.Frame;
import com.simulation.wellmanager.rest.dto.FrameDTO;
import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.dto.request.FrameUpdateCreateRequestDTO;
import com.simulation.wellmanager.rest.dto.status.SuccessInfoDTO;
import com.simulation.wellmanager.rest.mapper.FrameDTOMapper;
import com.simulation.wellmanager.rest.mapper.FrameRequestDTOMapper;
import com.simulation.wellmanager.service.FrameService;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
public class AdminController {

    private final FrameService frameService;

    private final FrameDTOMapper frameDTOMapper;

    private final FrameRequestDTOMapper frameRequestDTOMapper;

    @GetMapping(value = "/get-all-frames")
    public ResponseEntity<List<FrameDTO>> getAllFrames() {

        final List<Frame> frames = this.frameService.getAll();

        final List<FrameDTO> frameDTOs = frames.stream()
            .map(this.frameDTOMapper::toFrameDTO)
            .toList();

        return ResponseEntity.ok(frameDTOs);
    }

    @GetMapping(value = "/get-frame")
    public ResponseEntity<FrameDTO> getFrameById(@RequestParam(name = "id") final Long frameId) {

        final Frame frame = this.frameService.getById(frameId);

        return ResponseEntity.ok(this.frameDTOMapper.toFrameDTO(frame));
    }

    @PostMapping(value = "/create-frame")
    public ResponseEntity<SuccessInfoDTO> createFrame(
        @RequestBody @Valid final FrameCreateRequestDTO frameCreateRequestDTO) {

        final Frame frame = this.frameRequestDTOMapper.toFrame(frameCreateRequestDTO);

        final Frame savedFrame = this.frameService.save(frame);

        return ResponseEntity.ok(SuccessInfoDTO.builder()
            .status(HttpStatus.OK.value())
            .message(String.format("Frame %s was successfully created", savedFrame.getId()))
            .build());
    }

    @PostMapping(value = "/create-frames")
    public ResponseEntity<SuccessInfoDTO> createFrames(
        @RequestBody @Valid final List<FrameCreateRequestDTO> frameCreateRequestDTOs) {

        final List<Frame> frames = frameCreateRequestDTOs
            .stream()
            .map(this.frameRequestDTOMapper::toFrame)
            .toList();

        final List<Frame> savedFrames = this.frameService.saveAll(frames);

        final String frameIds = savedFrames.stream()
            .map(frame -> String.valueOf(frame.getId()))
            .collect(Collectors.joining(", "));

        return ResponseEntity.ok(SuccessInfoDTO.builder()
            .status(HttpStatus.OK.value())
            .message(String.format("Frames %s were successfully created", frameIds))
            .build());
    }

    @PostMapping(value = "/update-frame")
    public ResponseEntity<FrameDTO> updateFrame(
        @RequestBody @Valid final FrameUpdateCreateRequestDTO frameUpdateRequestDTO) {

        final Frame frameToUpdate = this.frameRequestDTOMapper.toFrame(frameUpdateRequestDTO);

        final Frame updatedFrame = this.frameService.update(frameToUpdate);

        return ResponseEntity.ok(this.frameDTOMapper.toFrameDTO(updatedFrame));
    }

    @PostMapping(value = "/delete-frame")
    public ResponseEntity<SuccessInfoDTO> deleteFrame(@RequestParam(name = "id") final Long frameId) {

        this.frameService.deleteById(frameId);

        return ResponseEntity.ok(SuccessInfoDTO.builder()
            .status(HttpStatus.OK.value())
            .message(String.format("Frame %s was successfully deleted", frameId))
            .build());
    }

}
