package com.travelplan.domain.travel.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.travel.dto.QTravelDto;
import com.travelplan.domain.travel.dto.TravelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<TravelDto> findByTravelInMemberOrderByDesc(String email) {
        return query.select(
                        new QTravelDto(travel.travelName, travel.startDate, travel.endDate)
                ).from(member)
                .join(member.user, user).on(user.email.eq(email))
                .join(member.travel, travel)
                .orderBy(travel.startDate.desc()).fetch();
    }

}
