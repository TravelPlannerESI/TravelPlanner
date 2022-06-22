package com.travelplan.global.config.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"country_iso_alp2"})
public class PcrContentData {
    private String country_iso_alp2; // ISO 2자리 코드
    private String country_nm;       // 한글 국가 명
    private String country_eng_nm;   // 영문 국가 명
    private String wrt_dt;           // 작성일
    private String title;            // 제목
    private String txt_origin_cn;    // 글 내용

    private PcrContentData(){}

    public PcrContentData(String country_iso_alp2, String country_nm, String country_eng_nm, String wrt_dt, String title, String txt_origin_cn) {
        this.country_iso_alp2 = country_iso_alp2;
        this.country_nm = country_nm;
        this.country_eng_nm = country_eng_nm;
        this.wrt_dt = wrt_dt;
        this.title = title;
        this.txt_origin_cn = txt_origin_cn;
    }
}
