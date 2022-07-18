package com.travelplan.domain.member.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.travel.domain.Travel;

import javax.persistence.EntityManager;

import static com.travelplan.domain.member.domain.QMember.member;
import static com.travelplan.domain.user.domain.QUser.user;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer findMemberId(Travel travel, String email) {
        return queryFactory
                .select(member.memberId)
                .from(member)
                .join(member.user, user)
                .where(member.travel.eq(travel).and(user.email.eq(email)))
                .fetchOne();
    }
}
