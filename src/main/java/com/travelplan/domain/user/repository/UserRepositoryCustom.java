package com.travelplan.domain.user.repository;

import com.travelplan.domain.user.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUserIdsByEmails(List<String> emails);

}
