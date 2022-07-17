package com.travelplan.global.utils.responsedto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ResponseData<T> {

    private T data;
    private String successCode;
    private String resultMsg;
    private String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    /**
     * 서버에서 만든 successCode와, 메세지 만 전달한다.
     *
     * @param successCode - HTTP.OK와 같은 HTTP 상태 코드가 아닌, 서버에서 따로 만든 A1, S1, SUCCESS, OK 등 커스텀 코드
     * @param resultMsg - successCode에 맞는 결과 메세지
     */
    public ResponseData(String successCode, String resultMsg) {
        this.successCode = successCode;
        this.resultMsg = resultMsg;
    }


    public ResponseData(T data, String successCode, String resultMsg) {
        this.data = data;
        this.successCode = successCode;
        this.resultMsg = resultMsg;
    }
}
