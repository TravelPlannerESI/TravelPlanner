package com.travelplan.global.utils.responsedto.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseConstant {

    SEARCH("SEARCH", "성공"),
    ADD("ADD", "저장 성공"),
    UPDATE("UPDATE", "수정 성공"),
    DELETE("DELETE", "삭제 성공");


    private final String successCode;
    private final String successMessage;


}
