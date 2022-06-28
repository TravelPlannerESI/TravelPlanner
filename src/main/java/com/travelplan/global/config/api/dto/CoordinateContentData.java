package com.travelplan.global.config.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"country_code"})
public class CoordinateContentData {
    private String country_code;
    private String country_name;
    private String lat;
    private String lng;

    public CoordinateContentData() {}

    public CoordinateContentData(String country_code, String country_name, String lat, String lng) {
        this.country_code = country_code;
        this.country_name = country_name;
        this.lat = lat;
        this.lng = lng;
    }
}
