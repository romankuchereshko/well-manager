package com.simulation.wellmanager.rest.dto.request;

import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class FrameUpdateCreateRequestDTO extends FrameCreateRequestDTO {

    private Long id;

}
