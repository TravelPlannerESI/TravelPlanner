package com.travelplan.global.exception;

import com.travelplan.global.exception.constant.ErrorConstant;
import com.travelplan.global.exception.dto.CustomErrorResult;
import com.travelplan.global.exception.dto.CustomFieldError;
import com.travelplan.global.exception.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
     * Global Form Validation 유효성 검사
     *
     * HTTP body의 데이터를 객체로 변환하는 과정에서 Validation오류가 있을 때 발생하는 Exception
     *
     * Validation Annotation
     * javax.validation.constraints.NotBlank
     * javax.validation.constraints.Length
     * javax.validation.constraints.NotNull
     *
     * @return ResponseEntity<ErrorResponse < List < CustomFieldError>>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<CustomFieldError>>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse<List<CustomFieldError>> response =
                CreateError.fieldErrors("처리중 에러가 발생했습니다.", e.getBindingResult(), messageSource);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * HTTP body의 JSON데이터를 객체로 변환하는 과정에서
     * field가 맞지 않거나, enum type과 맞지 않을 때 발생하는 Exception
     *
     * @return ResponseEntity<ErrorResponse<String>>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<String>> httpMessageNotReadableException(HttpMessageNotReadableException e) {

        ErrorResponse<String> response = CreateError.errorResult("올바른 정보를 입력해주세요.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TempException.class)
    public ResponseEntity<ErrorResponse<String>> tempException(TempException e) {

        ErrorResponse<String> response = CreateError.errorResult(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
