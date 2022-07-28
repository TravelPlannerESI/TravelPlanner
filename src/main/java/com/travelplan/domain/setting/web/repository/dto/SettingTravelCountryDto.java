package com.travelplan.domain.setting.web.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingTravelCountryDto {

    private Integer travelId;
    private String travelName;
    private String countryName;
    private String inviteCode;
    private String createdBy;
    private JoinStatus joinStatus;

    @QueryProjection
    public SettingTravelCountryDto(int travelId, String travelName, String countryName, String inviteCode) {
        this.travelId = travelId;
        this.travelName = travelName;
        this.countryName = countryName;
        this.inviteCode = inviteCode;
    }


    @QueryProjection
    public SettingTravelCountryDto(Travel travel, Country country, Member member) {
        this.travelId = travel.getTravelId();
        this.travelName = travel.getTravelName();
        this.countryName = country.getCountryName();
        this.inviteCode = travel.getInviteCode();
        this.createdBy = travel.getCreatedBy();
        this.joinStatus = member.getJoinStatus();
    }
}
