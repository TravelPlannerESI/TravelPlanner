package com.travelplan.domain.travel.dto;

import com.travelplan.domain.travel.domain.Travel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TravelInviteInfoDto  {
    public TravelInviteInfoDto(Travel travel) {
        this.travelName = travel.getTravelName();
        this.startDate = travel.getStartDate();
        this.endDate = travel.getEndDate();
        this.email = travel.getCreatedBy();
    }

    private Integer travelId;
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String email;

}
