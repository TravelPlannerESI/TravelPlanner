package com.travelplan.global.config.api.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WarningContentData {
    private Integer alarm_lvl;
    private String country_eng_nm;
    private String country_iso_alp2;
    private String country_nm;
    private String written_dt;

    public WarningContentData(Integer alarm_lvl, String country_eng_nm, String country_iso_alp2, String country_nm, String written_dt) {
        this.alarm_lvl = alarm_lvl;
        this.country_eng_nm = country_eng_nm;
        this.country_iso_alp2 = country_iso_alp2;
        this.country_nm = country_nm;
        this.written_dt = written_dt;
    }

    private WarningContentData(){};
}
