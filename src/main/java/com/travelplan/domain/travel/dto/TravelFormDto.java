package com.travelplan.domain.travel.dto;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.web.dto.CountryDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TravelFormDto {

    @NotBlank
    private String travelName;
    @NotNull
    private CountryDto countryDto;
    @NotNull
    private LocalDateTime lastModifiedDate;
    @NotBlank
    private String lastModifiedName;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotBlank
    private String createUserId;
    private String inviteCode;

}
