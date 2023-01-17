package com.simulation.wellmanager.service;

import java.util.List;

import oil.station.domain.frame.Frame;

public interface FrameService {

    List<Frame> getAll();

    Frame getById(Long frameId);

    boolean save(Frame frame);

    boolean saveAll(List<Frame> frames);

    Frame update(Frame frame);

    void deleteById(Long frameId);

}
