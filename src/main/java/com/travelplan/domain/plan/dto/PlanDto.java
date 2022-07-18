package com.travelplan.domain.plan.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlanDto {

    private Integer planId;

    private Integer days;

    private LocalDate currentDay;

    public PlanDto(int planId, int days, LocalDate currentDay) {
        this.planId = planId;
        this.days = days;
        this.currentDay = currentDay;
    }

}
