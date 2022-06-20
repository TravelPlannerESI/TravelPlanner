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
    private Integer currentCount;

    private List<ContentData> data;
}