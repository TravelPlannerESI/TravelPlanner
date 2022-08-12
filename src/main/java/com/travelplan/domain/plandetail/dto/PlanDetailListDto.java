package com.travelplan.domain.plandetail.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.global.entity.code.TravelTheme;
import com.travelplan.global.entity.code.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class PlanDetailListDto {

    private Integer planDetailId;
    private Integer planId;

    private String destinationName;
    private Integer days;
    private String cost;
    private String memo;
    private Vehicle vehicle;
    private TravelTheme travelTheme;

    private String lat;
    private String lng;

    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private PlanDetailListDto(){}

    public PlanDetailListDto(PlanDetail planDetail) {
        this.planDetailId = planDetail.getPlanDetailId();
        this.planId = planDetail.getPlan().getPlanId();
        this.destinationName = planDetail.getDestinationName();
        this.cost = planDetail.getCost();
        this.memo = planDetail.getMemo();
        this.vehicle = planDetail.getVehicle();
        this.travelTheme = planDetail.getTravelTheme();
        this.lat = planDetail.getLat();
        this.lng = planDetail.getLng();
        this.departureTime = planDetail.getDepartureTime();
        this.arrivalTime = planDetail.getArrivalTime();
    }

    @QueryProjection
    public PlanDetailListDto(Integer planDetailId, Integer planId,
                             String destinationName, Integer days, String cost,
                             String memo, Vehicle vehicle, TravelTheme travelTheme, String lat,
                             String lng, LocalTime departureTime, LocalTime arrivalTime) {
        this.planDetailId = planDetailId;
        this.planId = planId;
        this.destinationName = destinationName;
        this.days = days;
        this.cost = cost;
        this.memo = memo;
        this.vehicle = vehicle;
        this.travelTheme = travelTheme;
        this.lat = lat;
        this.lng = lng;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

}
