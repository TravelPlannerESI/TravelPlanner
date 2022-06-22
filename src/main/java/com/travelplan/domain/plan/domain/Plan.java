package com.travelplan.domain.plan.domain;

import com.travelplan.domain.travel.domain.Travel;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

//    @OneToMany(mappedBy = "plan")
//    private List<PlanDetail> planDetails = new ArrayList<>();

    private Integer days;


}
