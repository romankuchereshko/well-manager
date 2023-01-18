package com.simulation.wellmanager.rest.mapper;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.dto.request.FrameUpdateCreateRequestDTO;
import oil.station.domain.frame.Frame;

@Component
public class FrameRequestDTOMapper {

    public Frame toFrame(final FrameCreateRequestDTO frameCreateRequestDTO) {
        return Frame.builder()
            .wellId(frameCreateRequestDTO.getWellId())
            .voltage(frameCreateRequestDTO.getVoltage())
            .current(frameCreateRequestDTO.getCurrent())
            .speed(frameCreateRequestDTO.getSpeed())
            .frequency(frameCreateRequestDTO.getFrequency())
            .pressure(frameCreateRequestDTO.getPressure())
            .temperature(frameCreateRequestDTO.getTemperature())
            .liquidFlowRate(frameCreateRequestDTO.getLiquidFlowRate())
            .build();
    }

    public Frame toFrame(final FrameUpdateCreateRequestDTO frameUpdateRequestDTO) {
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
