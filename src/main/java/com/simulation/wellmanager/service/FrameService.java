package com.simulation.wellmanager.service;

import java.util.List;
import java.util.UUID;

import oil.station.domain.frame.Frame;

public interface FrameService {

    List<Frame> saveAll(List<Frame> frames);

    Frame save(Frame frame);

    Frame update(Frame frame);

    List<Frame> getAll();

    Frame getById(UUID frameId);

    void deleteById(UUID frameId);

}
