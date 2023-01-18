package com.simulation.wellmanager.service;

import java.util.List;

import oil.station.domain.frame.Frame;

public interface FrameService {

    List<Frame> getAll();

    Frame getById(Long frameId);

    Frame save(Frame frame);

    List<Frame> saveAll(List<Frame> frames);

    Frame update(Frame frame);

    void deleteById(Long frameId);

}
