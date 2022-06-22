package com.travelplan.global.config.api.constant;

public interface RestTemplateConst {
    public static final String BASE_DIR = "http://apis.data.go.kr";
    // 격리 단계 API
    public static final String WARNING_API_URL = "/1262000/TravelAlarmService2/getTravelAlarmList2";
    // PCR 정보 API
    public static final String PCR_API_URL = "/1262000/CountryCovid19SafetyServiceNew/getCountrySafetyNewsListNew";

    // 파라미터 값
    public static final String SERVICE_KEY_VALUE = "9Q4zVRGrJSpT04lu40y11GsFga4BWVpdabVl3UpFZUwAo4YblPW46yyYuWgLPyZ/31Le/wAjM4SnQaTIZtZXvw==";
    public static final String RETURN_TYPE_VALUE = "JSON";
    public static final String NUM_OF_ROWS_VALUE = "200";
    public static final String PAGE_NO_VALUE = "1";

    // 파라미터 정보
    static final String SERVICE_KEY = "serviceKey={serviceKey}";    // 서비스 키
    static final String RETURN_TYPE = "returnType={returnType}";    // 리턴 타입(JSON, XML)
    static final String NUM_OF_ROWS = "numOfRows={numOfRows}";      // 가져올 컨텐츠 수
    static final String PAGE_NO = "pageNo={pageNo}";                // 페이지 번호

    // 파라미터 정보 조합
    public static final String PARAMS = "?&" + SERVICE_KEY + "&" + RETURN_TYPE + "&" + NUM_OF_ROWS + "&" + PAGE_NO;

    // 최종 API URL
    public static final String WARNING_API = BASE_DIR + WARNING_API_URL + PARAMS;
    public static final String PCR_API = BASE_DIR + PCR_API_URL + PARAMS;
}
