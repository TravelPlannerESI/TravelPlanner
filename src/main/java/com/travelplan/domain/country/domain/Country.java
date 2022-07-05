package com.travelplan.domain.country.domain;

import com.travelplan.domain.covid.domain.Covid;
import com.travelplan.global.config.api.dto.CountryWithCoordinateFormDto;
import com.travelplan.global.entity.base.BaseDateEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;
//    @Column(name = "country_iso_alp2")
//    private String countryIsoAlp2;    // ISO 2자리 코드
    private String countryName;       // 국가명
    private String lat;               // 위도
    private String lng;               // 경도

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_iso_alp2")
    private Covid covid;

    public Country(CountryWithCoordinateFormDto dto) {
//        this.countryIsoAlp2 = dto.getCountry_iso_alp2();
        this.countryName = dto.getCountry_nm();
        this.lat = dto.getLat();
        this.lng = dto.getLng();
    }


    //
//    @OneToMany(mappedBy = "country")
//    private List<Travel> travels = new ArrayList<>();


}
