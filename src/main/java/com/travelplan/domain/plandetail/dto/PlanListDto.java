package com.travelplan.domain.plandetail.dto;

import com.travelplan.domain.plan.domain.Plan;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlanListDto {

    private Integer planId;

    private Integer days;

    private LocalDate currentDay;

    public PlanListDto() {}

    public PlanListDto(Plan plan) {
        this.planId = plan.getPlanId();
        this.days = plan.getDays();
        this.currentDay = plan.getCurrentDay();
    }

}
