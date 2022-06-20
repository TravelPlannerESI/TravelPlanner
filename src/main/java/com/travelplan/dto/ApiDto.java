package com.travelplan.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiDto {

    private Integer numOfRows;
    private Integer pageNo;
    private Integer resultCode;
    private String resultMsg;
    private Integer totalCount;

    private List<ContentData> data;
}

@Data
class ContentData {
    private String alarm_lvl;
    private String continent_cd;
    private String continent_eng_nm;
    private String continent_nm;
    private String country_eng_nm;
    private String country_iso_alp2;
    private String country_nm;
    private String dang_map_download_url;
    private String flag_download_url;
    private String map_download_url;
    private String region_ty;
    private String remark;
    private String written_dt;
}