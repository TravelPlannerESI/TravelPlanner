package com.travelplan.domain.country.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.covid.domain.Covid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountryCovidInfoForm {

    private String countryIsoAlp2;    // ISO 2자리 코드
    private String countryNm;       // 국가명
    private String countryEngNm;      // 영문 국가 명
    private String title;               // 제목
    private String txtOriginCn;       // 글 내용
    private Integer alarmLvl;          // 경보 레벨
    private String lat;               // 위도
    private String lng;               // 경도


    @QueryProjection
    public CountryCovidInfoForm(Country country, Covid covid) {
        this.countryIsoAlp2 = covid.getCountryIsoAlp2();
        this.countryNm = covid.getCountryNm();
        this.countryEngNm = covid.getCountryEngNm();
        this.title = covid.getTitle();
        this.txtOriginCn = covid.getTxtOriginCn();
        this.alarmLvl = covid.getAlarmLvl();
        this.lat = country.getLat();
        this.lng = country.getLng();
    }
}
