package com.travelplan.global.config.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PcrDto {

    List<PcrContentData> data;

    @Data
    public static class PcrContentData {
        private String country_iso_alp2; // ISO 2자리 코드
        private String country_nm;       // 한글 국가 명
        private String country_eng_nm;   // 영문 국가 명
        private String wrt_dt;           // 작성일
        private String title;            // 제목
        private String txt_origin_cn;    // 글 내용
    }

}
