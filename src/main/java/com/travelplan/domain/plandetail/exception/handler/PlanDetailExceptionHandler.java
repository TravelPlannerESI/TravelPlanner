package com.travelplan.domain.plandetail.exception.handler;

import com.travelplan.domain.plandetail.exception.NotExistPlanException;
import com.travelplan.global.exception.GlobalControllerAdvice;
import com.travelplan.global.exception.constant.ErrorConstant;
import com.travelplan.global.exception.dto.CustomErrorResult;
import com.travelplan.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.travelplan.domain.plandetail")
public class PlanDetailExceptionHandler {

    @ExceptionHandler(NotExistPlanException.class)
    public ResponseEntity<ErrorResponse<CustomErrorResult>> handleNotExistPlanException(NotExistPlanException e){

        ErrorResponse<CustomErrorResult> response = GlobalControllerAdvice.makeErrorResult(e.getMessage(), ErrorConstant.TEMP);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
