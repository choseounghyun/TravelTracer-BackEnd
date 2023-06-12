package com.project.travelTracer.checkpoint.repository;

import com.project.travelTracer.checkpoint.entity.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
}
