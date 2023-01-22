package com.simulation.wellmanager.rest.mapper;

import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.dto.request.FrameUpdateCreateRequestDTO;
import com.simulation.library.domain.Frame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrameRequestDTOMapper {

    Frame toFrame(FrameCreateRequestDTO frameCreateRequestDTO);

    Frame toFrame(FrameUpdateCreateRequestDTO frameUpdateRequestDTO);

}
