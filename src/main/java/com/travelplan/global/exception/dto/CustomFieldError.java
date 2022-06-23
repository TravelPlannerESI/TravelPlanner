package com.travelplan.global.exception.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomFieldError {

    private String field;
    private String rejectedValue;
    private String message;

    private CustomFieldError(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public static List<CustomFieldError> makeFieldErrors(BindingResult bindingResult, MessageSource messageSource) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        return fieldErrorList.stream()
                .map((fieldError) ->
                        new CustomFieldError(
                                fieldError.getField(),
                                fieldError.getRejectedValue().toString(),
                                messageSource.getMessage(fieldError, Locale.KOREA)
                        )
                )
                .collect(Collectors.toList());
    }

}
