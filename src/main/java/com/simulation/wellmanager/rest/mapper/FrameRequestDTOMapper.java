package com.simulation.wellmanager.rest.mapper;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.rest.dto.FrameRequestDTO;
import com.simulation.wellmanager.rest.dto.FrameUpdateRequestDTO;
import oil.station.domain.frame.Frame;

@Component
public class FrameRequestDTOMapper {

    public Frame toFrame(final FrameRequestDTO frameRequestDTO) {
        return Frame.builder()
            .wellId(frameRequestDTO.getWellId())
            .voltage(frameRequestDTO.getVoltage())
            .current(frameRequestDTO.getCurrent())
            .speed(frameRequestDTO.getSpeed())
            .frequency(frameRequestDTO.getFrequency())
            .pressure(frameRequestDTO.getPressure())
            .temperature(frameRequestDTO.getTemperature())
            .liquidFlowRate(frameRequestDTO.getLiquidFlowRate())
            .build();
    }

    public Frame toFrame(final FrameUpdateRequestDTO frameUpdateRequestDTO) {
        return Frame.builder()
            .id(frameUpdateRequestDTO.getId())
            .wellId(frameUpdateRequestDTO.getWellId())
            .voltage(frameUpdateRequestDTO.getVoltage())
            .current(frameUpdateRequestDTO.getCurrent())
            .speed(frameUpdateRequestDTO.getSpeed())
            .frequency(frameUpdateRequestDTO.getFrequency())
            .pressure(frameUpdateRequestDTO.getPressure())
            .temperature(frameUpdateRequestDTO.getTemperature())
            .liquidFlowRate(frameUpdateRequestDTO.getLiquidFlowRate())
            .build();
    }

}
