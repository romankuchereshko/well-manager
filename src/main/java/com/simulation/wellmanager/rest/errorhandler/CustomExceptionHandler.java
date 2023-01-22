package com.simulation.wellmanager.rest.errorhandler;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simulation.wellmanager.domain.exception.FrameException;
import com.simulation.wellmanager.rest.dto.status.ErrorInfoDTO;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfoDTO handleConstraintViolationException(
        final ConstraintViolationException constraintViolationException) {

        final Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        return ErrorInfoDTO.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(this.buildErrorMessage(violations))
            .timestamp(LocalDateTime.now().toString())
            .build();
    }

    private String buildErrorMessage(final Set<ConstraintViolation<?>> violations) {
        String errorMessage;
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" ").append(violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }

        return errorMessage;
    }
}
