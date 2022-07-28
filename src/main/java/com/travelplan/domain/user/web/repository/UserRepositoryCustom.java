package com.travelplan.domain.user.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.user.domain.QUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.user.domain.QUser.*;

@Repository
public class UserRepositoryCustom {

    private final JPAQueryFactory factory;

    public UserRepositoryCustom(EntityManager em) {
        this.factory = new JPAQueryFactory(em);
    }

    public List<String> findByEmailExceptInvited(String userEmail, List<String> invitedMembers) {
        return factory
                .select(user.email)
                .from(user)
                .where(user.email.startsWith(userEmail).and(user.email.notIn(invitedMembers)))
                .fetch();

    }


}
