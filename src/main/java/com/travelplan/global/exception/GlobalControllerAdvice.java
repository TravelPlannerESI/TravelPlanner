package com.travelplan.global.exception;

import com.travelplan.global.exception.dto.CustomErrorResult;
import com.travelplan.global.exception.dto.CustomFieldError;
import com.travelplan.global.exception.dto.ErrorConstant;
import com.travelplan.global.exception.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final MessageSource messageSource;

    /**
     * RequestBody를 이용해 데이터를 받을때 예외가 터지면 MethodArgumentNotValidException이 터진다.
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<CustomFieldError>>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse<List<CustomFieldError>> response =
                makeFieldErrors("처리중 에러가 발생했습니다.", e.getBindingResult(), messageSource);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TempException.class)
    public ResponseEntity<ErrorResponse<CustomErrorResult>> tempException(TempException e) {

        ErrorResponse<CustomErrorResult> response = makeErrorResult(e.getMessage(), ErrorConstant.TEMP);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }






    /**
     * errorResult 단건 객체
     * @param errorMsg - 메세지
     * @param constant -
     * @return ErrorResponse<CustomFieldError>
     */
    private ErrorResponse<CustomErrorResult> makeErrorResult(String errorMsg, ErrorConstant constant) {
        return new ErrorResponse(errorMsg, constant.getDetailErrorMsg(), constant.getAaa(), constant.getBbb());
    }


    /**
     * FieldErrors 다건 리스트
     * @param errorMsg - 에러메세지
     * @param bindingResult - 필드 에러 정보
     * @param messageSource - 필드 에러 메시지 출력
     * @return ErrorResponse<List<CustomFieldError>>
     */
    private ErrorResponse<List<CustomFieldError>> makeFieldErrors(String errorMsg, BindingResult bindingResult, MessageSource messageSource) {
        return new ErrorResponse<>(errorMsg, bindingResult, messageSource);
    }



}
