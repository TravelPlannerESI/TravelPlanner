package com.travelplan.domain.member.repository;

import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {
}
