package com.travelplan.domain.plandetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelplan.global.entity.code.TravelTheme;
import com.travelplan.global.entity.code.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@Setter
public class PlanDetailModifyFormDto {

    private Integer planId;
    private Integer travelId;

    @NotBlank
    private String destinationName;

    private String cost;
    private String memo;
    private Vehicle vehicle;
    private TravelTheme travelTheme;

    private String lat;
    private String lng;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H[H]:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "H[H]:mm")
    private LocalTime departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H[H]:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "H[H]:mm")
    private LocalTime arrivalTime;

}
