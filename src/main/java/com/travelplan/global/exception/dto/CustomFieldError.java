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

    private String field;           // 오류가 발생한 필드
    private String rejectedValue;   // 사용자가 입력한 값
    private String message;         // 오류 메시지

    private CustomFieldError(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }


    /**
     * Validation오류가 발생한 필드들이 정보를 가지고 있는 BindingResult를 받는다.
     * 각 field에서 필요한 부분만 추출해 CustomFieldError 객체로 변환해 List에 담아서 반환한다.
     *
     * @param bindingResult : validation 오류가 발생한 field들의 정보를 담은 객체
     * @param messageSource : messageSource.getMessage에 fieldError객체를 넣으면  errors.properties에 정의한 메시지를 읽어 반환해준다.
     * @return List<CustomFieldError>
     */
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
