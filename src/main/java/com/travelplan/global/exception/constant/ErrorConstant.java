package com.travelplan.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorConstant {

    TEMP("디테일한 예외 메시지 입니다.", "aaa", "bbb");

    private final String detailErrorMsg;
    private final String aaa;
    private final String bbb;

}
