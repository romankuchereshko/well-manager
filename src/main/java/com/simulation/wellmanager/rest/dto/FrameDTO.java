package com.simulation.wellmanager.rest.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class FrameDTO implements Serializable {

    private UUID id;

    private Long wellId;

    private Double voltage;

    private Double current;

    private Double speed;

    private Double frequency;

    private Double pressure;

    private Double temperature;

    private Double liquidFlowRate;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
