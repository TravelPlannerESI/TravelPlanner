package com.travelplan.domain.user.repository;

import com.travelplan.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> , UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    List<User> findByEmailStartsWith(String userEmail);
    List<User> findByEmailIn(List<String> members);
}