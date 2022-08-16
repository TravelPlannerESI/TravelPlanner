package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.global.entity.code.TravelTheme;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChartDataDto {

    private Integer days;
    private String cost;
    private TravelTheme travelTheme;

    @QueryProjection
    public ChartDataDto(Integer days, String cost, TravelTheme travelTheme) {
        this.days = days;
        this.cost = cost;
        this.travelTheme = travelTheme;
    }

}
