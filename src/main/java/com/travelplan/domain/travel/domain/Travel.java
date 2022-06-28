package com.travelplan.domain.travel.domain;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.global.entity.base.BaseDateAndCreatorEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Travel extends BaseDateAndCreatorEntity {

    public Travel(TravelFormDto travelFormDto) {
        this.travelName = travelFormDto.getTravelName();
        this.startDate = travelFormDto.getStartDate();
        this.endDate = travelFormDto.getEndDate();
        this.createUserId = travelFormDto.getCreateUserId();
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
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "invite_code")
    private String inviteCode;

//    @OneToMany(mappedBy = "travel")
//    private List<Plan> plans = new ArrayList<>();

    public void saveInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public void addCountry(Country country) {
        this.country = country;
    }

}
