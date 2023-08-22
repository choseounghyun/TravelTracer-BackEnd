package com.project.travelTracer.checkpoint.dto;

import com.project.travelTracer.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckPointDto {
    int locationId;

    String locationName;

    double longitude;

    double latitude;

    long createtime;

    public void CheckPointDto(int locationId, String locationName, double longitude, double latitude, long createtime){
        this.locationId = locationId;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createtime = createtime;
    }

    public void CheckPointDto(){

    }
    public Checkpoint toEntity() {
        return Checkpoint.bulider().locationId(locationId)
                .locationName(locationName)
                .longitude(longitude)
                .latitude(latitude)
                .createtime(createtime)
                .build();
    }
}
