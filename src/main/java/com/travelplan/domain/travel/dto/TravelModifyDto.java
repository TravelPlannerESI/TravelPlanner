package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class TravelModifyDto {

    private String travelName;
    private String countryIsoAlp2;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> membersEmail;
    private Integer totalCost;

    @QueryProjection
    public TravelModifyDto(String travelName, String countryIsoAlp2, LocalDate startDate, LocalDate endDate, Integer totalCost) {
        this.travelName = travelName;
        this.countryIsoAlp2 = countryIsoAlp2;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

}
