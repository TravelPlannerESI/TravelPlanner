package com.travelplan.domain.plandetail.dto;

import com.travelplan.global.entity.code.TravelTheme;
import com.travelplan.global.entity.code.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@Setter
public class PlanDetailAddFormDto {

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

    private LocalTime departureTime;
    private LocalTime arrivalTime;

}
