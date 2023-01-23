package com.simulation.wellmanager.rest.validator;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.simulation.library.domain.properties.FrameConfigurations;
import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.validator.constraint.FrameValueListConstraint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FrameValueListConstraintValidator extends AbstractFrameValueValidator implements
    ConstraintValidator<FrameValueListConstraint, List<FrameCreateRequestDTO>> {

    public FrameValueListConstraintValidator(final FrameConfigurations frameConfigurations) {
        super(frameConfigurations);
    }

    @Override
    public boolean isValid(final List<FrameCreateRequestDTO> requestDTOs, final ConstraintValidatorContext context) {

        if (Objects.isNull(requestDTOs) || requestDTOs.isEmpty()) {
            log.info("Request is [{}] can't be null or empty", requestDTOs);
            return false;
        }

        return requestDTOs.stream()
            .map(this::isFrameRequestValid)
            .noneMatch(b -> Objects.equals(b, Boolean.FALSE));
    }
}
