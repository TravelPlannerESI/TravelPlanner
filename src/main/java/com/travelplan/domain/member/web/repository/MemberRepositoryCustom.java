package com.travelplan.domain.member.web.repository;

import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.domain.QMember;
import com.travelplan.domain.travel.domain.QTravel;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.user.domain.QUser;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.travelplan.domain.member.domain.QMember.*;
import static com.travelplan.domain.travel.domain.QTravel.travel;
import static com.travelplan.domain.user.domain.QUser.*;
import static com.travelplan.global.entity.code.MemberRole.ADMIN;

@Repository
public class MemberRepositoryCustom {

    private final JPAQueryFactory factory;

    public MemberRepositoryCustom(EntityManager em) {
        this.factory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public Boolean existByTravelIdAndUserId(Integer travelId, String userEmail) {
        Integer fetchOne = factory
                .selectOne()
                .from(member)
                .where(
                        user.userId.eq(select(user.userId)
                                        .from(user)
                                        .where(user.email.eq(userEmail)))
                        .and(travel.travelId.eq(travelId))
                        .and(member.joinStatus.eq(JoinStatus.YES))
                )
                .fetchFirst();
        return fetchOne != null;
    }


    @Transactional(readOnly = true)
    public Integer findMemberId(Travel travel, String email) {
        return factory
                .select(member.memberId)
                .from(member)
                .join(member.user, user)
                .where(member.travel.eq(travel).and(user.email.eq(email)))
                .fetchOne();
    }

    public Boolean isMemberAdmin(Integer travelId, String email) {
        return factory
                .selectOne()
                .from(member)
                .where(
                        member.user.userId.eq(
                                select(user.userId)
                                .from(user)
                                .where(user.email.eq(email))
                        ).and(travel.travelId.eq(travelId))
                                .and(member.memberRole.eq(ADMIN))
                )
                .fetchFirst() != null;
    }

    public List<String> findByTravelId(Integer travelId) {
        return factory
                .select(user.email)
                .from(member)
                .join(member.user, user)
                .where(member.travel.travelId.eq(travelId))
                .fetch();
    }

    public boolean isMemberInTravel(Integer travelId , String email){
        return factory
                .select(member.count())
                .from(member)
                .join(member.user, user)
                .where(user.email.eq(email).and(member.travel.travelId.eq(travelId)))
                .fetchOne() > 0 ? true : false;
    }

}
