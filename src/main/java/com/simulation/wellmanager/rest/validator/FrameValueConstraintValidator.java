package com.simulation.wellmanager.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.simulation.library.domain.properties.FrameConfigurations;
import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import com.simulation.wellmanager.rest.validator.constraint.FrameValueConstraint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FrameValueConstraintValidator extends AbstractFrameValueValidator implements
    ConstraintValidator<FrameValueConstraint, FrameCreateRequestDTO> {

    public FrameValueConstraintValidator(final FrameConfigurations frameConfigurations) {
        super(frameConfigurations);
    }

    @Override
    public boolean isValid(final FrameCreateRequestDTO requestDTO, final ConstraintValidatorContext context) {
        return this.isFrameRequestValid(requestDTO);
    }

}