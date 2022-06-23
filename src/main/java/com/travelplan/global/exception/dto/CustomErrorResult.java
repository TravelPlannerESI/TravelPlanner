package com.travelplan.global.exception.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResult {

    private String detailErrorMsg;
    private String aaa;
    private String bbb;

    public CustomErrorResult(String detailErrorMsg, String aaa, String bbb) {
        this.detailErrorMsg = detailErrorMsg;
        this.aaa = aaa;
        this.bbb = bbb;
    }

}
