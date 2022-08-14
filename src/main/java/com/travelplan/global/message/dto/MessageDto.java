package com.travelplan.global.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto<T> {
    private T data;
    private boolean isAlarm;

    public MessageDto(T data, boolean isAlarm) {
        this.data = data;
        this.isAlarm = isAlarm;
    }
}
