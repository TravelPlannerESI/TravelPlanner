package com.travelplan.domain.member.repository.custom;

import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.travel.domain.Travel;

public interface MemberRepositoryCustom {

    Integer findMemberId(Travel travel, String email);
}
