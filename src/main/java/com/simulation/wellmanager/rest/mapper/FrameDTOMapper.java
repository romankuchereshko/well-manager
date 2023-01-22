package com.simulation.wellmanager.rest.mapper;

import java.time.ZoneOffset;

import com.simulation.wellmanager.rest.dto.FrameDTO;
import oil.station.domain.frame.Frame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = ZoneOffset.class)
public interface FrameDTOMapper {

    @Mapping(target = "createdAt", expression = "java(frame.getCreatedAt().atOffset(ZoneOffset.UTC))")
    @Mapping(target = "updatedAt", expression = "java(frame.getUpdatedAt().atOffset(ZoneOffset.UTC))")
    FrameDTO toFrameDTO(Frame frame);

}
