package com.simulation.wellmanager.storage.repository;

import java.util.List;

import com.simulation.library.domain.Frame;

public interface FrameRepository {

    List<Frame> getAll();

    Frame getById(Long frameId);

    Frame save(Frame frame);

    List<Frame> saveAll(List<Frame> frames);

    Frame update(Frame frame);

    void deleteById(Long frameId);
}
