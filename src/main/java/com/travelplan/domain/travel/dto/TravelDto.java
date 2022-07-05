package com.travelplan.domain.travel.dto;

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

    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String inviteCode;

}
