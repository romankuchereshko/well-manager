package com.simulation.wellmanager.service;

import com.simulation.library.domain.Frame;

public interface CriticalValuesCalculator {

    void calculateAndSetIsValueCritical(Frame frame);

}
