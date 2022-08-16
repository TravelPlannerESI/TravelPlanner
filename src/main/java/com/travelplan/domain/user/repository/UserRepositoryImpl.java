package com.travelplan.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.user.domain.QUser;
import com.travelplan.domain.user.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.user.domain.QUser.*;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findUserIdsByEmails(List<String> emails) {
        return queryFactory
                .select(user)
                .from(user)
                .where(user.email.in(emails))
                .fetch();
    }
}
