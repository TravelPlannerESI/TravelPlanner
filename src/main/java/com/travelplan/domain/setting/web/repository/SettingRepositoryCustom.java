package com.travelplan.domain.setting.web.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.setting.web.repository.dto.QSettingTravelCountryDto;
import com.travelplan.domain.setting.web.repository.dto.QSettingTravelMembers;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelCountryDto;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelMembers;
import com.travelplan.domain.travel.domain.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.country.domain.QCountry.country;
import static com.travelplan.domain.member.domain.QMember.member;
import static com.travelplan.domain.travel.domain.QTravel.travel;
import static com.travelplan.domain.user.domain.QUser.user;

@Repository
public class SettingRepositoryCustom {

    private final JPAQueryFactory factory;

    public SettingRepositoryCustom(EntityManager em) {
        this.factory = new JPAQueryFactory(em);
    }


    public Page<SettingTravelCountryDto> selectTravelsByLoginUser(String loginUserEmail, Pageable pageable) {

        List<SettingTravelCountryDto> fetchList = factory
                .select(
                        new QSettingTravelCountryDto(
                                travel.travelId,
                                travel.travelName,
                                country.countryName,
                                travel.inviteCode
                        )
                )
                .from(travel)
                .leftJoin(travel.country, country)
                .where(travel.createdBy.eq(loginUserEmail))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(travel.startDate.desc())
                .fetch();

        JPAQuery<Long> totalCount = factory
                .select(travel.count())
                .from(travel)
                .leftJoin(travel.country, country)
                .where(travel.createdBy.eq(loginUserEmail));

        Page<SettingTravelCountryDto> page = PageableExecutionUtils.getPage(fetchList, pageable, totalCount::fetchOne);

        return page;
    }

    public List<SettingTravelMembers> selectMembers(int travelId) {
        return factory
                .select(new QSettingTravelMembers(user, member))
                .from(member)
                .leftJoin(member.user, user)
                .where(
                        member.travel.eq(JPAExpressions
                                .select(travel)
                                .from(travel)
                                .where(travel.travelId.eq(travelId))
                        ))
                .fetch();
    }



    public Page<SettingTravelCountryDto> selectInvitedTravel(String email, Pageable pageable) {

        List<SettingTravelCountryDto> fetchList = factory
                .select(new QSettingTravelCountryDto(travel, country, member))
                .from(member)
                .leftJoin(member.user, user)
                .leftJoin(member.travel, travel)
                .leftJoin(travel.country, country)
                .where(user.email.eq(email), travel.createdBy.ne(email))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(travel.startDate.desc())
                .fetch();

        JPAQuery<Long> totalCount = factory
                .select(travel.count())
                .from(member)
                .leftJoin(member.user, user)
                .leftJoin(member.travel, travel)
                .leftJoin(travel.country, country)
                .where(user.email.eq(email), travel.createdBy.ne(email));

        return PageableExecutionUtils.getPage(fetchList, pageable, totalCount::fetchOne);

    }
}
