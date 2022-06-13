package com.travelplan.travel.domain;

import com.travelplan.travel.domain.code.JoinStatus;
import com.travelplan.travel.domain.code.MemberRole;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
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

}
