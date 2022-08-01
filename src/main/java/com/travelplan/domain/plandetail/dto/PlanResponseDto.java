package com.travelplan.domain.plandetail.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlanResponseDto {


    private String travelName;
    private List<PlanListDto> plans;
    private List<PlanDetailListDto> planDetails;

    public PlanResponseDto() { }

    public PlanResponseDto(String travelName, List<PlanListDto> plans, List<PlanDetailListDto> planDetails) {
        this.travelName = travelName;
        this.plans = plans;
        this.planDetails = planDetails;
    }

}
