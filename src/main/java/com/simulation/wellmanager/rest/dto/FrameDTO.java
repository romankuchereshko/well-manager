package com.simulation.wellmanager.rest.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class FrameDTO implements Serializable {

    private Long id;

    private Long wellId;

    private Double voltage;

    private Double current;

    private Double speed;

    private Double frequency;

    private Double pressure;

    private Double temperature;

    private Double liquidFlowRate;

    private Boolean isCritical;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private OffsetDateTime updatedAt;

}
