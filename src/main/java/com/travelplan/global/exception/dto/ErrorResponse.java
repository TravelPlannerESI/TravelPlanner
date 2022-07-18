package com.travelplan.global.exception.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ErrorResponse<T> {

    private String current_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private String errorMsg;
    private T errors;

    public ErrorResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ErrorResponse(String errorMsg, String detailErrorMsg, String aaa, String bbb) {
        this.errorMsg = errorMsg;
        this.errors = (T) new CustomErrorResult(detailErrorMsg, aaa, bbb);
    }

    public ErrorResponse(String errorMsg, BindingResult bindingResult, MessageSource messageSource) {
        this.errorMsg = errorMsg;
        this.errors = (T) CustomFieldError.makeFieldErrors(bindingResult, messageSource);
    }

}



