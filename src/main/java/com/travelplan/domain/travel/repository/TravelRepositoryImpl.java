package com.travelplan.domain.travel.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.country.domain.QCountry;
import com.travelplan.domain.covid.domain.QCovid;
import com.travelplan.domain.member.domain.QMember;
import com.travelplan.domain.travel.domain.QTravel;
import com.travelplan.domain.travel.dto.QTravelModifyDto;
import com.travelplan.domain.travel.dto.TravelModifyDto;
import com.travelplan.domain.user.domain.QUser;
import com.travelplan.domain.user.domain.User;
import com.travelplan.global.entity.code.JoinStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.country.domain.QCountry.*;
import static com.travelplan.domain.covid.domain.QCovid.*;
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
    public Boolean existAlreadyJoin(Integer travelId, List<User> users) {
        return queryFactory
                .selectOne()
                .from(travel)
                .leftJoin(member)
                .on(travel.travelId.eq(travelId).and(travel.travelId.eq(member.travel.travelId)).and(member.user.in(users)))
                .where(member.joinStatus.eq(YES).or(member.joinStatus.eq(EMPTY)))
                .fetchFirst() != null;
    }

    @Override
    public TravelModifyDto findModifyDtoByTravelId(Integer travelId) {
        return queryFactory
                .select(new QTravelModifyDto(travel.travelName, covid.countryNm, travel.startDate, travel.endDate, travel.totalCost))
                .from(travel)
                .join(travel.country, country)
                .join(travel.country.covid, covid)
                .where(travel.travelId.eq(travelId))
                .fetchOne();
    }
}
