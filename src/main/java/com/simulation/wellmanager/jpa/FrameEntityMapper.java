package com.simulation.wellmanager.jpa;

import com.simulation.wellmanager.jpa.entity.FrameEntity;
import oil.station.domain.frame.Frame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrameEntityMapper {

    FrameEntity toEntity(Frame frame);

    Frame toDomain(FrameEntity frameEntity);

}
