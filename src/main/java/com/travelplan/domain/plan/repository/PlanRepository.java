package com.travelplan.domain.plan.repository;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByTravel(Travel travel);

}
