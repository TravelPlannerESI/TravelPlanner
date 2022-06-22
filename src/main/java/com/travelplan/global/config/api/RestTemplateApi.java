package com.travelplan.global.config.api;

import com.travelplan.global.config.api.dto.CountryFormDto;
import com.travelplan.global.config.api.dto.PcrDto;
import com.travelplan.global.config.api.dto.WarningDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class RestTemplateApi {

    private final RestTemplate restTemplate = new RestTemplate();

    // 호출 시 참조하는 인스턴스(캐싱 Data)
    public static CountryFormDto countryFormDto = null;

    public static final String BASE_DIR = "http://apis.data.go.kr";
    // 격리 단계 API
    public static final String WARNING_API_URL = "/1262000/TravelAlarmService2/getTravelAlarmList2";
    // PCR 정보 API
    public static final String PCR_API_URL = "/1262000/CountryCovid19SafetyServiceNew/getCountrySafetyNewsListNew";

    // 파라미터 정보
    private static final String SERVICE_KEY = "serviceKey={serviceKey}";    // 서비스 키
    private static final String RETURN_TYPE = "returnType={returnType}";    // 리턴 타입(JSON, XML)
    private static final String NUM_OF_ROWS = "numOfRows={numOfRows}";      // 가져올 컨텐츠 수
    private static final String PAGE_NO = "pageNo={pageNo}";                // 페이지 번호

    // 파라미터 값
    public static final String SERVICE_KEY_VALUE = "9Q4zVRGrJSpT04lu40y11GsFga4BWVpdabVl3UpFZUwAo4YblPW46yyYuWgLPyZ/31Le/wAjM4SnQaTIZtZXvw==";
    public static final String RETURN_TYPE_VALUE = "JSON";
    public static final String NUM_OF_ROWS_VALUE = "200";
    public static final String PAGE_NO_VALUE = "1";

    // 파라미터 정보 조합
    public static final String PARAMS = "?&" + SERVICE_KEY + "&" + RETURN_TYPE + "&" + NUM_OF_ROWS + "&" + PAGE_NO;

    // 최종 API URL
    public static final String WARNING_API = BASE_DIR + WARNING_API_URL + PARAMS;
    public static final String PCR_API = BASE_DIR + PCR_API_URL + PARAMS;

    @PostConstruct
    public void init() {

        // 요청 생성
        HttpEntity request = getJsonRequest();
        WarningDto warningResult = fetch(WARNING_API, HttpMethod.GET, request, WarningDto.class, getFullParam());

        PcrDto pcrResult = fetch(PCR_API, HttpMethod.GET, request, PcrDto.class, getFullParam());

//        warningResult.getData().stream().forEach(System.out::println);

        System.out.println(warningResult.getData());
        System.out.println(pcrResult.getData());
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
        return new String[]{SERVICE_KEY_VALUE, RETURN_TYPE_VALUE, NUM_OF_ROWS_VALUE, PAGE_NO_VALUE};
    }

}
