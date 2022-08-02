package com.travelplan.domain.member.domain;

import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.user.domain.User;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    public Member(Travel travel, User user, JoinStatus joinStatus, MemberRole memberRole) {
        this.travel = travel;
        this.user = user;
        this.joinStatus = joinStatus;
        this.memberRole = memberRole;
    }

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    /* 비즈니스 로직 */

    public void setJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
        if (joinStatus.name().equals("YES"))
            memberRole = MemberRole.USER;
        else
            memberRole = MemberRole.GUEST;
    }
}
