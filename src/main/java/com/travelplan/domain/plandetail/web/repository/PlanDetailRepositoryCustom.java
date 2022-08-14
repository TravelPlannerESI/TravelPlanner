package com.travelplan.domain.plandetail.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.domain.QPlan;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.domain.plandetail.dto.PlanDetailListDto;
import com.travelplan.domain.plandetail.dto.QPlanDetailListDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.plan.domain.QPlan.*;
import static com.travelplan.domain.plandetail.domain.QPlanDetail.*;

@Repository
public class PlanDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlanDetailRepositoryCustom(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public List<PlanDetailListDto> findByPlans(List<Plan> plans) {
        return queryFactory
                .select(new QPlanDetailListDto(planDetail.planDetailId, plan.planId, planDetail.destinationName, plan.days, planDetail.cost, planDetail.memo, planDetail.vehicle,
                        planDetail.travelTheme, planDetail.lat, planDetail.lng, planDetail.departureTime, planDetail.arrivalTime))
                .from(planDetail)
                .join(planDetail.plan, plan)
                .where(
                        plan.in(plans)
                )
                .orderBy(plan.days.asc(), planDetail.arrivalTime.asc().nullsLast())
                .fetch();
    }

    public List<PlanDetailListDto> finByPlanId(Integer planId) {
        return queryFactory
                .select(new QPlanDetailListDto(planDetail.planDetailId, plan.planId, planDetail.destinationName, plan.days, planDetail.cost, planDetail.memo, planDetail.vehicle,
                        planDetail.travelTheme, planDetail.lat, planDetail.lng, planDetail.departureTime, planDetail.arrivalTime))
                .from(planDetail)
                .join(planDetail.plan, plan)
                .where(planDetail.plan.planId.eq(planId))
                .orderBy(planDetail.arrivalTime.asc().nullsLast())
                .fetch();
    }
}
