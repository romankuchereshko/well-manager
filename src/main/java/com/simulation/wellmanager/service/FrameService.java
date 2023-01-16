package com.simulation.wellmanager.service;

import java.util.List;

import oil.station.domain.frame.Frame;

public interface FrameService {

    List<Frame> saveAll(List<Frame> frames);

    Frame save(Frame frame);

    Frame update(Frame frame);

    List<Frame> getAll();

    Frame getById(Long frameId);

    void deleteById(Long frameId);

}
