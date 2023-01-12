package com.simulation.wellmanager.rest.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.simulation.wellmanager.rest.dto.FrameDTO;
import oil.station.domain.frame.Frame;


@Component
public class FrameDTOMapper {

    public FrameDTO toFrameDTO(final Frame frame) {
        final FrameDTO frameDTO = new FrameDTO();

        frameDTO.setId(frame.getId());
        frameDTO.setWellId(frame.getWellId());
        frameDTO.setVoltage(frame.getVoltage());
        frameDTO.setCurrent(frame.getCurrent());
        frameDTO.setSpeed(frame.getSpeed());
        frameDTO.setFrequency(frame.getFrequency());
        frameDTO.setPressure(frame.getPressure());
        frameDTO.setTemperature(frame.getTemperature());
        frameDTO.setLiquidFlowRate(frame.getLiquidFlowRate());
        frameDTO.setCreatedAt(frame.getCreatedAt().atOffset(ZoneOffset.UTC));
        frameDTO.setUpdatedAt(frame.getUpdatedAt().atOffset(ZoneOffset.UTC));

        return frameDTO;
    }

}
