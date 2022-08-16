package com.travelplan.domain.plan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.domain.QPlan;
import com.travelplan.domain.travel.domain.QTravel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.plan.domain.QPlan.*;

@Repository
public class PlanRepositoryImpl implements PlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlanRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Plan> findByTravelIdAndDays(Integer travelId, long days) {
        return queryFactory
                .select(plan)
                .from(plan)
                .where(plan.travel.travelId.eq(travelId).and(plan.days.lt(days)))
                .fetch();
    }

    @Override
    public List<Plan> findByTravelIdAndDaysLimit(Integer travelId, long days) {
        return queryFactory
                .select(plan)
                .from(plan)
                .where(plan.travel.travelId.eq(travelId))
                .orderBy(plan.days.desc())
                .limit(days)
                .fetch();
    }

}
