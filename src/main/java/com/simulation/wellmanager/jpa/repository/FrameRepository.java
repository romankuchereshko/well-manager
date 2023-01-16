package com.simulation.wellmanager.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simulation.wellmanager.jpa.entity.FrameEntity;

@Repository
public interface FrameRepository extends JpaRepository<FrameEntity, Long> {

}
