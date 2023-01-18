package com.simulation.wellmanager.storage.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simulation.wellmanager.storage.entity.FrameEntity;

@Repository
public interface FrameEntityRepository extends JpaRepository<FrameEntity, Long> {

}
