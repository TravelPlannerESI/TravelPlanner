package com.travelplan.domain.plan.domain;

import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.global.entity.base.BaseDateAndCreatorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"days", "currentDay"})
public class Plan extends BaseDateAndCreatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

//    @OneToMany(mappedBy = "plan")
//    private List<PlanDetail> planDetails = new ArrayList<>();

    private Integer days;

    private LocalDate currentDay;

    public Plan(Travel travel, Integer days, LocalDateTime currentDay) {
        this.travel = travel;
        this.days = days;
        this.currentDay = LocalDate.now();
    }
}
