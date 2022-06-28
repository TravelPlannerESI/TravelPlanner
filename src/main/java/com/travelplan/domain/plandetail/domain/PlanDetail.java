package com.travelplan.domain.plandetail.domain;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.global.entity.base.BaseDateAndCreatorEntity;
import com.travelplan.global.entity.code.Vehicle;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
public class PlanDetail extends BaseDateAndCreatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_detail_id")
    private Integer planDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String cost;
    private String memo;

    @Column(name = "position_x")
    private Integer positionX;
    @Column(name = "position_y")
    private Integer positionY;

    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;
    private LocalTime time;

}
