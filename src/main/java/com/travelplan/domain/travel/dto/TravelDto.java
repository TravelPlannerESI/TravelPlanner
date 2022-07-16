package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.travel.domain.Travel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TravelDto {

    public TravelDto(TravelFormDto travelFormDto) {
        this.travelName = travelFormDto.getTravelName();
        this.startDate = travelFormDto.getStartDate();
        this.endDate = travelFormDto.getEndDate();
        this.inviteCode = travelFormDto.getInviteCode();
    }

    public TravelDto(Travel travel) {
        this.travelName = travel.getTravelName();
        this.startDate = travel.getStartDate();
        this.endDate = travel.getEndDate();
        this.inviteCode = travel.getInviteCode();
    }
    @QueryProjection
    public TravelDto(String travelName, LocalDate startDate, LocalDate endDate,Integer travelId) {
        this.travelName = travelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inviteCode = "";
        this.travelId = travelId;
    }

    private Integer travelId;
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String inviteCode;

}
