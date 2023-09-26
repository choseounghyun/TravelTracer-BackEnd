package com.project.travelTracer.location.dto;

import com.project.travelTracer.location.entity.CheckPoint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CheckPointDto {
    @NotNull
    int locationId;
    @NotBlank(message ="locationName 비어있음")
    String locationName;

    double longitude;

    double latitude;
    @NotNull
    // 생성자 이름을 클래스명과 동일하게 변경
    public CheckPointDto(int locationId, String locationName, double longitude, double latitude ) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CheckPointDto() {
    }

    public CheckPoint toEntity() {
        return CheckPoint.builder()
                .locationId(locationId)
                .locationName(locationName)
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
