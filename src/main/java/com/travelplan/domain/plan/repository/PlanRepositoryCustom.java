package com.travelplan.domain.plan.repository;

import com.travelplan.domain.plan.domain.Plan;

import java.util.List;

public interface PlanRepositoryCustom {

    List<Plan> findByTravelIdAndDays(Integer travelId, long days);

    List<Plan> findByTravelIdAndDaysLimit(Integer travelId, long days);
}
