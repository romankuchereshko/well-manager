package com.simulation.wellmanager.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("frame")
public class FrameRedisEntity implements Serializable {

    @Id
    private UUID id;

    private UUID wellId;

    private Double voltage;

    private Double current;

    private Double speed;

    private Double frequency;

    private Double pressure;

    private Double temperature;

    private Double liquidFlowRate;

    private LocalDateTime creationDateTime;

}
