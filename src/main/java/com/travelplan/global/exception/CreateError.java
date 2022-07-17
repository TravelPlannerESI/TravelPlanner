package com.travelplan.global.exception;

import com.travelplan.global.exception.constant.ErrorConstant;
import com.travelplan.global.exception.dto.CustomErrorResult;
import com.travelplan.global.exception.dto.CustomFieldError;
import com.travelplan.global.exception.dto.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;

public class CreateError {

    /**
     * errorResult 단건 객체 - 메시지만 반환
     *
     * @param errorMsg - 메세지
     * @return ErrorResponse<String>
     */
    public static ErrorResponse<String> errorResult(String errorMsg) {
        return new ErrorResponse<>(errorMsg);
    }

    /**
     * errorResult 단건 객체
     *
     * @param errorMsg - 메세지
     * @param constant - detail한 예외 정보를 입력할 수 있다.
     * @return ErrorResponse<CustomFieldError>
     */
    public static  ErrorResponse<CustomErrorResult> errorResult(String errorMsg, ErrorConstant constant) {
        return new ErrorResponse<>(errorMsg, constant.getDetailErrorMsg(), constant.getAaa(), constant.getBbb());
    }

    /**
     * FieldErrors 다건 리스트
     *
     * @param errorMsg      - 에러메세지
     * @param bindingResult - 필드 에러 정보
     * @param messageSource - 필드 에러 메시지 출력
     * @return ErrorResponse<List < CustomFieldError>>
     */
    public static  ErrorResponse<List<CustomFieldError>> fieldErrors(String errorMsg, BindingResult bindingResult, MessageSource messageSource) {
        return new ErrorResponse<>(errorMsg, bindingResult, messageSource);
    }

}
