package com.travelplan.domain.plan.repository;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>, PlanRepositoryCustom {

    List<Plan> findByTravel(Travel travel);

    @Modifying(clearAutomatically = true)
    @Query("update Plan p set p.days = p.days + :plusDays where p.travel.travelId = :travelId")
    int daysPlus(@Param("plusDays") long plusDays, @Param("travelId") Integer travelId);

    @Modifying(clearAutomatically = true)
    @Query("update Plan p set p.days = p.days - :minusDays where p.travel.travelId = :travelId")
    int daysMinus(@Param("minusDays") long minusDays, @Param("travelId") Integer travelId);
}
