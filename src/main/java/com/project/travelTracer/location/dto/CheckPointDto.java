package com.project.travelTracer.location.dto;

import com.project.travelTracer.location.entity.CheckPoint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CheckPointDto {
    int locationId;

    String locationName;

    double longitude;

    double latitude;

    LocalDateTime createtime;

    public void CheckPointDto(int locationId, String locationName, double longitude, double latitude, LocalDateTime createtime){
        this.locationId = locationId;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createtime = createtime;
    }

    public void CheckPointDto(){

    }
    public CheckPoint toEntity() {
        return CheckPoint.builder().locationId(locationId)
                .locationName(locationName)
                .longitude(longitude)
                .latitude(latitude)
                .createtime(createtime)
                .build();
    }
}
