package com.travelplan.domain.country.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

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
