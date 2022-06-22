package com.travelplan.global.config.api;

import com.travelplan.global.config.api.constant.RestTemplateConst;
import com.travelplan.global.config.api.dto.CountryFormDto;
import com.travelplan.global.config.api.dto.PcrDto;
import com.travelplan.global.config.api.dto.WarningDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class RestTemplateApi {

    private static final RestTemplate restTemplate = new RestTemplate();

    // 호출 시 참조하는 인스턴스(캐싱 Data)
    public static CountryFormDto countryFormDto = null;

    @PostConstruct
    public void init() {

        // 요청 생성
        HttpEntity request = getJsonRequest();
        WarningDto warningDto = fetch(RestTemplateConst.WARNING_API, HttpMethod.GET, request, WarningDto.class, getFullParam());
        PcrDto pcrDto = fetch(RestTemplateConst.PCR_API, HttpMethod.GET, request, PcrDto.class, getFullParam());

        List<CountryFormDto> result = CountryFormDto.of(warningDto, pcrDto);
        for (CountryFormDto formDto : result) {
            System.out.println("formDto = " + formDto);
        }

    }

    /**
     * HttpHeader 생성 메소드 ( JSON )
     * @return HttpHeaders
     */
    private HttpEntity getJsonRequest() {
        // 해더 생성
        HttpHeaders headers = new HttpHeaders();

        // ContentType, Accept 설정
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity(headers);
    }

    /**
     * API 호출 메서드
     *
     * @param url
     * @param httpMethod
     * @param request
     * @param dtoClass
     * @return T
     */
    private <T> T fetch(String url,
                              HttpMethod httpMethod,
                              HttpEntity request,
                              Class<T> dtoClass,
                              String... params) {

        return restTemplate.exchange(
                url,
                httpMethod,
                request,
                dtoClass,
                params
        ).getBody();
    }

    private String[] getFullParam() {
        return new String[]{RestTemplateConst.SERVICE_KEY_VALUE, RestTemplateConst.RETURN_TYPE_VALUE,
                RestTemplateConst.NUM_OF_ROWS_VALUE, RestTemplateConst.PAGE_NO_VALUE};
    }
}
