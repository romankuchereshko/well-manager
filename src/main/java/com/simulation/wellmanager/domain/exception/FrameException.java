package com.simulation.wellmanager.domain.exception;

import java.io.Serial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class FrameException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5721748379984509521L;

    private final String message;

}