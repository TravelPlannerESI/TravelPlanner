package com.travelplan.domain.plandetail.repository;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {

    List<PlanDetail> findByPlan(Plan plan);

}
