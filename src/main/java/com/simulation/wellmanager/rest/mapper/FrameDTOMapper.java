package com.simulation.wellmanager.rest.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.rest.dto.FrameDTO;
import oil.station.domain.frame.Frame;

@Component
public class FrameDTOMapper {

    public FrameDTO toFrameDTO(final Frame frame) {
        return FrameDTO.builder()
            .id(frame.getId())
            .wellId(frame.getWellId())
            .voltage(frame.getVoltage())
            .current(frame.getCurrent())
            .speed(frame.getSpeed())
            .frequency(frame.getFrequency())
            .pressure(frame.getPressure())
            .temperature(frame.getTemperature())
            .liquidFlowRate(frame.getLiquidFlowRate())
            .isCritical(frame.getIsCritical())
            .createdAt(frame.getCreatedAt().atOffset(ZoneOffset.UTC))
            .updatedAt(frame.getUpdatedAt().atOffset(ZoneOffset.UTC))
            .build();
    }

    public Frame toFrame(final FrameDTO frameDTO) {
        return Frame.builder()
            .id(frameDTO.getId())
            .wellId(frameDTO.getWellId())
            .voltage(frameDTO.getVoltage())
            .current(frameDTO.getCurrent())
            .speed(frameDTO.getSpeed())
            .frequency(frameDTO.getFrequency())
            .pressure(frameDTO.getPressure())
            .temperature(frameDTO.getTemperature())
            .isCritical(frameDTO.getIsCritical())
            .liquidFlowRate(frameDTO.getLiquidFlowRate())
            .build();
    }

}
