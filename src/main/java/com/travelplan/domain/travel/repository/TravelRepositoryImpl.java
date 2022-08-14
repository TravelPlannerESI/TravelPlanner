package com.travelplan.domain.travel.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.member.domain.QMember;
import com.travelplan.domain.travel.domain.QTravel;
import com.travelplan.domain.user.domain.QUser;
import com.travelplan.domain.user.domain.User;
import com.travelplan.global.entity.code.JoinStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.member.domain.QMember.*;
import static com.travelplan.domain.travel.domain.QTravel.*;
import static com.travelplan.global.entity.code.JoinStatus.EMPTY;
import static com.travelplan.global.entity.code.JoinStatus.YES;

@Repository
public class TravelRepositoryImpl implements TravelRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TravelRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Boolean existAlreadyJoin(List<User> users) {
        return queryFactory
                .selectOne()
                .from(travel)
                .leftJoin(member)
                .on(travel.travelId.eq(member.travel.travelId).and(member.user.in(users)))
                .where(member.joinStatus.eq(YES).or(member.joinStatus.eq(EMPTY)))
                .fetchFirst() != null;
    }
}
