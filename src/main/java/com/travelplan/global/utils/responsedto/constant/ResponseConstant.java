package com.travelplan.global.utils.responsedto.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseConstant {

    SEARCH("SEARCH", "성공"),
    ADD("ADD", "저장되었습니다."),
    UPDATE("UPDATE", "수정되었습니다."),
    DELETE("DELETE", "삭제되었습니다."),
    RESPONSE("RESPONSE", "완료되었습니다."),
    RESEND("RE", "초대 요청을 다시 보냈습니다.");


    private final String successCode;
    private final String successMessage;


}
