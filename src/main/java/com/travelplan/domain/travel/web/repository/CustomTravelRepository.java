package com.travelplan.domain.travel.web.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.country.domain.QCountry;
import com.travelplan.domain.covid.domain.QCovid;
import com.travelplan.domain.travel.dto.*;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
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
import static com.travelplan.domain.member.domain.QMember.member;
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
                        new QTravelDto(travel.travelName, travel.startDate, travel.endDate, travel.travelId)
                ).from(member)
                .join(member.user, user)
                .join(member.travel, travel)
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
        return query.select(new QTravelCountryInfoDto(travel, country, covid.countryIsoAlp2))
                .from(travel)
                .leftJoin(travel.country, country)
                .leftJoin(country.covid, covid)
                .where(travel.travelId.eq(travelId))
                .fetchOne();
    }

}
