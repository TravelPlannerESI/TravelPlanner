package com.travelplan.global.config.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryFormDto {
    private String country_iso_alp2; // ISO 2자리 코드
    private String country_nm;       // 한글 국가 명
    private String country_eng_nm;   // 영문 국가 명
    private String title;            // 제목
    private String txt_origin_cn;    // 글 내용
    private String alarm_lvl;        // 경보 레벨
    private String written_dt;       // 작성일(경보)
    private String wrt_dt;           // 작성일(PCR)
}
