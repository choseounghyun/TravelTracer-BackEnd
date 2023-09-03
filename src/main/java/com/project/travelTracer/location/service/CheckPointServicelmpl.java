package com.project.travelTracer.location.service;


import com.project.travelTracer.location.dto.CheckPointDto;
import com.project.travelTracer.location.entity.CheckPoint;
import com.project.travelTracer.location.repository.CheckPointRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckPointServicelmpl implements CheckPointService
{

    private final CheckPointRepository checkPointRepository;

    @Override
    public void CheckpointManager(CheckPointDto CheckPointDto) throws Exception {
        CheckPoint checkpoint = CheckPointDto.toEntity();

        checkPointRepository.save(checkpoint);
    }
}
