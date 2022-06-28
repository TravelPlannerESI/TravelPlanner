package com.travelplan.domain.country.domain;

import com.travelplan.global.entity.base.BaseDateEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country extends BaseDateEntity {

    public Country(String countryStatus, String countryName) {
        this.countryStatus = countryStatus;
        this.countryName = countryName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;
    private String countryStatus;
    private String countryName;
    private Double lat;
    private Double lng;

//
//    @OneToMany(mappedBy = "country")
//    private List<Travel> travels = new ArrayList<>();


}
