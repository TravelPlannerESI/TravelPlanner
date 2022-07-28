package com.travelplan.domain.setting.web.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.user.domain.User;
import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingTravelMembers {

    private String userName;
    private String email;
    private JoinStatus joinStatus;


    @QueryProjection
    public SettingTravelMembers(User user, Member member) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.joinStatus = member.getJoinStatus();
    }
}
