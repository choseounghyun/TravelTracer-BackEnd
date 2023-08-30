package com.project.travelTracer.location.repository;

import com.project.travelTracer.location.entity.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {

    Optional<CheckPoint> findBylocationId(int locationId);

}
