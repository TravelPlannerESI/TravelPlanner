package com.travelplan.global.config.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class WarningDto {

    List<WarningContentData> data;

    @Data
    static class WarningContentData {
        private String alarm_lvl;
        private String country_eng_nm;
        private String country_iso_alp2;
        private String country_nm;
        private String written_dt;
    }

}
