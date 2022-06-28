package com.travelplan.global.config.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"country_code"})
public class TravelMakerContentData {
    private String country_code;
    private String country_name;
    private String country_EN_name;
    private String lat;
    private String lng;

    public TravelMakerContentData() {}

    public TravelMakerContentData(String country_code, String country_name, String country_EN_name, String lat, String lng) {
        this.country_code = country_code;
        this.country_name = country_name;
        this.country_EN_name = country_EN_name;
        this.lat = lat;
        this.lng = lng;
    }
}
