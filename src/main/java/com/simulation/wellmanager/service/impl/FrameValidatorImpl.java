package com.simulation.wellmanager.service.impl;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.simulation.library.domain.Frame;
import com.simulation.library.domain.Value;
import com.simulation.library.domain.properties.FrameConfigurations;
import com.simulation.wellmanager.domain.exception.FrameException;
import com.simulation.wellmanager.service.FrameValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FrameValidatorImpl implements FrameValidator {

    private final FrameConfigurations frameConfigurations;

    public void validateFrame(final Frame frame) {
        log.info("Starting validating frame [{}]", frame);

        if (Objects.isNull(frame)) {
            final String message = "Given frame %s is null";
            log.info(message);
            throw new FrameException(message);
        }

        final Value voltage = this.frameConfigurations.getVoltage();
        final Value current = this.frameConfigurations.getCurrent();
        final Value speed = this.frameConfigurations.getSpeed();
        final Value frequency = this.frameConfigurations.getFrequency();
        final Value temperature = this.frameConfigurations.getTemperature();
        final Value pressure = this.frameConfigurations.getPressure();
        final Value liquidFlowRate = this.frameConfigurations.getLiquidFlowRate();

        boolean isValidRequest = Stream.of(
                this.isValidValue(voltage, frame.getVoltage()),
                this.isValidValue(current, frame.getCurrent()),
                this.isValidValue(speed, frame.getSpeed()),
                this.isValidValue(frequency, frame.getFrequency()),
                this.isValidValue(temperature, frame.getTemperature()),
                this.isValidValue(pressure, frame.getPressure()),
                this.isValidValue(liquidFlowRate, frame.getLiquidFlowRate())
            )
            .noneMatch(b -> Objects.equals(b, Boolean.FALSE));

        if (isValidRequest) {
            log.info("Frame [{}] is valid", frame);
        } else {
            final String message = String.format("Given frame %s is not valid", frame);
            log.info(message);
            throw new FrameException(message);
        }
    }

    private boolean isValidValue(final Value configValue, final Double valueToCheck) {
        return Objects.nonNull(valueToCheck)
            && valueToCheck >= configValue.getMinValue() && valueToCheck <= configValue.getMaxValue();
    }

}
