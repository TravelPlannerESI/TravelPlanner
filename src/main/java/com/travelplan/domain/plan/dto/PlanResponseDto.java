package com.travelplan.domain.plan.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class PlanResponseDto {

    private String travelName;
    private String travelDate;
    private List<PlanDto> plans;

    public PlanResponseDto() { }

    public PlanResponseDto(String travelName, List<PlanDto> plans) {
        this.travelName = travelName;
        this.plans = plans;
        this.setTravelDate(plans);
    }

    private void setTravelDate(List<PlanDto> list) {
        StringBuilder sb = new StringBuilder();
        String s = list.get(0).getCurrentDay().toString();
        String e = list.get(list.size()-1).getCurrentDay().toString();
        String startDate = s.substring(5);
        String endDate = e.substring(5);

        this.travelDate = sb.append(startDate).append(" ~ ").append(endDate).toString();
    }

}
