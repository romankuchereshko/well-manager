package com.simulation.wellmanager.rest.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class FrameCreateRequestDTO implements Serializable {

    private Long wellId;

    private Double voltage;

    private Double current;

    private Double speed;

    private Double frequency;

    private Double pressure;

    private Double temperature;

    private Double liquidFlowRate;

    private Boolean IsCritical;

}
