package com.simulation.wellmanager.rest.mapper;

import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.dto.request.FrameUpdateCreateRequestDTO;
import oil.station.domain.frame.Frame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrameRequestDTOMapper {

    Frame toFrame(FrameCreateRequestDTO frameCreateRequestDTO);

    Frame toFrame(FrameUpdateCreateRequestDTO frameUpdateRequestDTO);

}
