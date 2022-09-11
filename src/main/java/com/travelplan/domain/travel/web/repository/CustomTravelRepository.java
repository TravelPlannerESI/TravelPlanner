package com.travelplan.domain.travel.web.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.country.domain.QCountry;
import com.travelplan.domain.covid.domain.QCovid;
import com.travelplan.domain.exchangerate.domain.QExchangeRate;
import com.travelplan.domain.plan.domain.QPlan;
import com.travelplan.domain.plandetail.domain.QPlanDetail;
import com.travelplan.domain.travel.dto.*;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import com.travelplan.global.entity.code.TravelTheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.country.domain.QCountry.*;
import static com.travelplan.domain.covid.domain.QCovid.*;
import static com.travelplan.domain.exchangerate.domain.QExchangeRate.*;
import static com.travelplan.domain.member.domain.QMember.member;
import static com.travelplan.domain.plan.domain.QPlan.*;
import static com.travelplan.domain.plandetail.domain.QPlanDetail.*;
import static com.travelplan.domain.travel.domain.QTravel.travel;
import static com.travelplan.domain.user.domain.QUser.user;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class CustomTravelRepository {

    private final JPAQueryFactory query;

    public CustomTravelRepository(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    public Page<TravelDto> findByTravelInMemberOrderByDesc(String email , Pageable pageable) {
        List<TravelDto> fetchContent = query.select(
                        new QTravelDto(travel.travelName, travel.startDate, travel.endDate, travel.travelId,travel.country.countryName)
                ).from(member)
                .join(member.user, user)
                .join(member.travel, travel)
                .join(travel.country,country)
                .where(member.joinStatus.eq(JoinStatus.YES).and(user.email.eq(email)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(travel.startDate.desc()).fetch();

        JPAQuery<Long> count = query.select(member.count())
                .from(member)
                .join(member.user, user)
                .where(user.email.eq(email).and(member.joinStatus.eq(JoinStatus.YES)));

        Page<TravelDto> page = PageableExecutionUtils.getPage(fetchContent, pageable, count::fetchOne);
        return page;
    }

    public List<TravelInviteDto> findByTravelMemberOrderByDesc(String email) {
        List<Integer> fetchTravelId = query.select(
                        travel.travelId
                ).from(member)
                .join(member.user, user)
                .join(member.travel, travel)
                .where(member.joinStatus.eq(JoinStatus.EMPTY).and(user.email.eq(email)))
                .fetch();

        List<TravelInviteDto> fetchContent = query.select(
                        new QTravelInviteDto(user.userName, travel.travelName, travel.travelId, user.userPicture)
                ).from(member)
                .join(member.user, user)
                .join(member.travel, travel)
                .where(travel.travelId.in(fetchTravelId).and(user.email.eq(travel.createdBy)))
                .orderBy(travel.startDate.desc()).fetch();

        return fetchContent;
    }


    public TravelCountryInfoDto findTravelCountryInfo(Integer travelId) {
        TravelCountryInfoDto fetchOne = query
                .select(
                        new QTravelCountryInfoDto(
                            travel.travelId, travel.travelName, travel.startDate, travel.endDate, travel.inviteCode, travel.totalCost,
                            country.countryName, covid.countryIsoAlp2,
                            exchangeRate.fxrt, exchangeRate.currSgn, exchangeRate.mtryUtNm
                        )
                )
                .from(travel)
                .leftJoin(travel.country, country)
                .leftJoin(country.covid, covid)
                .leftJoin(exchangeRate)
                .on(covid.countryIsoAlp2.eq(exchangeRate.cntySgn))
                .where(travel.travelId.eq(travelId))
                .fetchOne();


        List<ChartDataDto> chartData = query
                .select(new QChartDataDto(plan.days, planDetail.cost, planDetail.travelTheme))
                .from(plan)
                .leftJoin(plan.planDetails, planDetail)
                .leftJoin(plan.travel, travel)
                .where(travel.travelId.eq(travelId)).fetch();

        fetchOne.setChartData(chartData);

        return fetchOne;
    }

}
