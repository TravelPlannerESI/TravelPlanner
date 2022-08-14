package com.travelplan.domain.travel.domain;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.dto.TravelModifyFormDto;
import com.travelplan.global.entity.base.BaseDateAndCreatorEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Travel extends BaseDateAndCreatorEntity {

    public Travel(TravelFormDto travelFormDto) {
        this.travelName = travelFormDto.getTravelName();
        this.startDate = travelFormDto.getStartDate();
        this.endDate = travelFormDto.getEndDate();
        this.totalCost = travelFormDto.getTotalCost();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Integer travelId;
    @Column(name = "travel_name")
    private String travelName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "invite_code")
    private String inviteCode;

    private Integer totalCost;

//    @OneToMany(mappedBy = "travel")
//    private List<Plan> plans = new ArrayList<>();

    public void saveInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public void addCountry(Country country) {
        this.country = country;
    }

    public void modifyTravel(TravelModifyFormDto dto) {
        this.travelName = dto.getTravelName();
        this.country = dto.getCountry();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.totalCost = dto.getTotalCost();
    }
}
