package com.simulation.wellmanager.redis;

import com.simulation.wellmanager.redis.entity.FrameRedisEntity;
import oil.station.domain.frame.Frame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrameRedisEntityMapper {

    FrameRedisEntity toEntity(Frame frame);

    Frame toDomain(FrameRedisEntity frameRedisEntity);

}
