package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.travel.domain.Travel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
public class TravelCountryInfoDto {


    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String inviteCode;
    private Integer totalCost;

    private String countryName;       // 국가명
    private String countryIsoAlp2;    // ISO 2자리 코드



    @QueryProjection
    public TravelCountryInfoDto(Travel travel, Country country, String countryIsoAlp2) {
        this.travelName = travel.getTravelName();
        this.startDate = travel.getStartDate();
        this.endDate = travel.getEndDate();
        this.inviteCode = travel.getInviteCode();
        this.totalCost = travel.getTotalCost();

        this.countryName = country.getCountryName();
        this.countryIsoAlp2 = countryIsoAlp2;
    }



}
