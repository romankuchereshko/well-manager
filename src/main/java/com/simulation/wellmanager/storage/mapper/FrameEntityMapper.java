package com.simulation.wellmanager.storage.mapper;

import com.simulation.wellmanager.storage.entity.FrameEntity;
import com.simulation.library.domain.Frame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrameEntityMapper {

    FrameEntity toEntity(Frame frame);

    Frame toDomain(FrameEntity frameEntity);

}
