package com.travelplan.domain.plandetail.domain;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plandetail.dto.PlanDetailAddFormDto;
import com.travelplan.global.entity.base.BaseDateAndCreatorEntity;
import com.travelplan.global.entity.code.TravelTheme;
import com.travelplan.global.entity.code.Vehicle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanDetail extends BaseDateAndCreatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_detail_id")
    private Integer planDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String destinationName;

    private String cost;
    private String memo;

    @Column(name = "lat")
    private String lat;
    @Column(name = "lng")
    private String lng;

    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private TravelTheme travelTheme;

    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public PlanDetail(PlanDetailAddFormDto dto) {
        this.destinationName = dto.getDestinationName();
        this.cost = dto.getCost();
        this.memo = dto.getMemo();
        this.lat = dto.getLat();
        this.lng = dto.getLng();
        this.vehicle = dto.getVehicle();
        this.travelTheme = dto.getTravelTheme();
        this.arrivalTime = dto.getArrivalTime();
        this.departureTime = dto.getDepartureTime();
    }
}
