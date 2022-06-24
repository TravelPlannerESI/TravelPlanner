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


    public ResponseData(T data, String successCode, String resultMsg) {
        this.data = data;
        this.successCode = successCode;
        this.resultMsg = resultMsg;
    }
}
