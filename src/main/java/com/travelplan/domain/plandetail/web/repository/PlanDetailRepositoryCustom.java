package com.travelplan.domain.plandetail.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.plandetail.domain.QPlanDetail.*;

@Repository
public class PlanDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlanDetailRepositoryCustom(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public List<PlanDetail> findByPlans(List<Plan> plans) {
        return queryFactory
                .select(planDetail)
                .from(planDetail)
                .where(
                        planDetail.plan.in(plans)
                )
                .fetch();
    }

    public List<PlanDetail> finByPlanId(Integer planId) {
        return queryFactory
                .select(planDetail)
                .from(planDetail)
                .where(planDetail.plan.planId.eq(planId))
                .fetch();
    }
}
