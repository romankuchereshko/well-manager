package com.simulation.wellmanager.rest.validator;

import java.util.Objects;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.simulation.library.domain.Value;
import com.simulation.library.domain.properties.FrameConfigurations;
import com.simulation.wellmanager.rest.dto.request.FrameCreateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FrameValueConstraintValidator implements ConstraintValidator<FrameValueConstraint, FrameCreateRequestDTO> {

    private final FrameConfigurations frameConfigurations;

    @Override
    public boolean isValid(final FrameCreateRequestDTO requestDTO, final ConstraintValidatorContext context) {
        log.info("Starting validating frame request [{}]", requestDTO);

        if (Objects.isNull(requestDTO)) {
            log.info("Frame request is null");
            return false;
        }

        final Value voltage = this.frameConfigurations.getVoltage();
        final Value current = this.frameConfigurations.getCurrent();
        final Value speed = this.frameConfigurations.getSpeed();
        final Value frequency = this.frameConfigurations.getFrequency();
        final Value temperature = this.frameConfigurations.getTemperature();
        final Value pressure = this.frameConfigurations.getPressure();
        final Value liquidFlowRate = this.frameConfigurations.getLiquidFlowRate();

        boolean isValidRequest = Stream.of(
                this.isValidValue(voltage, requestDTO.getVoltage(), requestDTO.getIsCritical()),
                this.isValidValue(current, requestDTO.getCurrent(), requestDTO.getIsCritical()),
                this.isValidValue(speed, requestDTO.getSpeed(), requestDTO.getIsCritical()),
                this.isValidValue(frequency, requestDTO.getFrequency(), requestDTO.getIsCritical()),
                this.isValidValue(temperature, requestDTO.getTemperature(), requestDTO.getIsCritical()),
                this.isValidValue(pressure, requestDTO.getPressure(), requestDTO.getIsCritical()),
                this.isValidValue(liquidFlowRate, requestDTO.getLiquidFlowRate(), requestDTO.getIsCritical())
            )
            .noneMatch(b -> Objects.equals(b, Boolean.FALSE));

        log.info("Frame request validation result is: [{}]", isValidRequest);
        return isValidRequest;
    }

    private boolean isValidValue(final Value configValue, final Double valueToCheck,
        final Boolean isRequestValueCritical) {

        if (Objects.isNull(isRequestValueCritical) ||
            !Objects.equals(isRequestValueCritical, this.checkIsCriticalValue(configValue, valueToCheck))) {

            log.info("Request isCritical field is [{}] which is not correct for values in the request",
                isRequestValueCritical);
            return false;
        }

        return Objects.nonNull(valueToCheck)
            && valueToCheck >= configValue.getMinValue() && valueToCheck <= configValue.getMaxValue();
    }

    private boolean checkIsCriticalValue(final Value configValue, final Double valueToCheck) {
        return (valueToCheck <= configValue.getMinCriticalValue() && valueToCheck >= configValue.getMinValue()) ||
            (valueToCheck >= configValue.getMaxCriticalValue() && valueToCheck <= configValue.getMaxValue());
    }

}