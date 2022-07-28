package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelInviteDto {

    @QueryProjection
    public TravelInviteDto(String invitee, String travelName, Integer travelId, String inviteePicture) {
        this.invitee = invitee;
        this.travelName = travelName;
        this.travelId = travelId;
        this.inviteePicture = inviteePicture;
    }

    String invitee;
    String travelName;
    Integer travelId;
    String inviteePicture;

}
