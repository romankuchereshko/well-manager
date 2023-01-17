package com.simulation.wellmanager.rest.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SuccessInfoDTO implements Serializable {

    private Integer status;

    private String message;

}
