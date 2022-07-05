package com.travelplan.domain.travel.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class TravelFormDto {

    @NotBlank
    private String travelName;
    @NotNull
    private String countryIsoAlp2;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private String inviteCode;

}
