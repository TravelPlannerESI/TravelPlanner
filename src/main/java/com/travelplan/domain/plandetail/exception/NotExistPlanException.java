package com.travelplan.domain.plandetail.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistPlanException extends RuntimeException{
    public NotExistPlanException() {
        super("계획이 존재하지 않습니다.");
    }

    public NotExistPlanException(String message){
        super(message);
    }
}
