package com.simulation.wellmanager.rest.errorhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simulation.wellmanager.domain.exception.FrameException;
import com.simulation.wellmanager.rest.dto.ErrorInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({FrameException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfoDTO handleFrameException(final FrameException exception) {
        return ErrorInfoDTO.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(exception.getMessage())
            .timestamp(LocalDateTime.now().toString())
            .build();
    }
}
