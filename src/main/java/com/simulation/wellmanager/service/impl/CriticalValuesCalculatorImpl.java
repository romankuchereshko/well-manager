package com.simulation.wellmanager.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.simulation.library.domain.Frame;
import com.simulation.library.domain.Value;
import com.simulation.library.domain.properties.FrameConfigurations;
import com.simulation.wellmanager.service.CriticalValuesCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class CriticalValuesCalculatorImpl implements CriticalValuesCalculator {

    private final FrameConfigurations frameConfigurations;

    @Override
    public void calculateAndSetIsValueCritical(final Frame frame) {
        log.info("Starting calculating is frame has critical value [{}]", frame);

        final Value voltage = this.frameConfigurations.getVoltage();
        final Value current = this.frameConfigurations.getCurrent();
        final Value speed = this.frameConfigurations.getSpeed();
        final Value frequency = this.frameConfigurations.getFrequency();
        final Value temperature = this.frameConfigurations.getTemperature();
        final Value pressure = this.frameConfigurations.getPressure();
        final Value liquidFlowRate = this.frameConfigurations.getLiquidFlowRate();

        boolean atLeastOneValueIsCritical = List.of(
                this.isCritical(voltage, frame.getVoltage()),
                this.isCritical(current, frame.getCurrent()),
                this.isCritical(speed, frame.getSpeed()),
                this.isCritical(frequency, frame.getFrequency()),
                this.isCritical(temperature, frame.getTemperature()),
                this.isCritical(pressure, frame.getPressure()),
                this.isCritical(liquidFlowRate, frame.getLiquidFlowRate())
            )
            .contains(Boolean.TRUE);

        if (!Objects.equals(frame.getIsCritical(), atLeastOneValueIsCritical)) {
            log.info("Frame had critical value: [{}] before calculation, but it was recalculated to: [{}]",
                frame.getIsCritical(),
                atLeastOneValueIsCritical);
        }

        frame.setIsCritical(atLeastOneValueIsCritical);
        log.info("Finished calculating frame critical value [{}]", frame);
    }

    private boolean isCritical(final Value configValue, final Double valueToCheck) {
        return (valueToCheck <= configValue.getMinCriticalValue() && valueToCheck >= configValue.getMinValue()) ||
            (valueToCheck >= configValue.getMaxCriticalValue() && valueToCheck <= configValue.getMaxValue());
    }

}
