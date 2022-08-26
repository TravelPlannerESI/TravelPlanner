package com.travelplan.global.config.api.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface RestTemplateConst {
    String BASE_DIR = "http://apis.data.go.kr";
    // 격리 단계 API
    String WARNING_API_URL = "/1262000/TravelAlarmService2/getTravelAlarmList2";
    // PCR 정보 API
    String PCR_API_URL = "/1262000/CountryCovid19SafetyServiceNew/getCountrySafetyNewsListNew";

    // 파라미터 값
    String SERVICE_KEY_VALUE = "9Q4zVRGrJSpT04lu40y11GsFga4BWVpdabVl3UpFZUwAo4YblPW46yyYuWgLPyZ/31Le/wAjM4SnQaTIZtZXvw==";
    String RETURN_TYPE_VALUE = "JSON";
    String NUM_OF_ROWS_VALUE = "200";
    String PAGE_NO_VALUE = "1";

    // 파라미터 정보
    String SERVICE_KEY = "serviceKey={serviceKey}";    // 서비스 키
    String RETURN_TYPE = "returnType={returnType}";    // 리턴 타입(JSON, XML)
    String NUM_OF_ROWS = "numOfRows={numOfRows}";      // 가져올 컨텐츠 수
    String PAGE_NO = "pageNo={pageNo}";                // 페이지 번호

    // 파라미터 정보 조합
    String PARAMS = "?" + SERVICE_KEY + "&" + RETURN_TYPE + "&" + NUM_OF_ROWS + "&" + PAGE_NO;

    // 최종 API URL
    String WARNING_API = BASE_DIR + WARNING_API_URL + PARAMS;
    String PCR_API = BASE_DIR + PCR_API_URL + PARAMS;
    String COORDINATE_API = "https://www.travelmakerkorea.com/api/newsList";


    /* -------------------------------------------------------- */
    // 환율 정보

    // 환율 정보 API
    String CURRENCY_API_URL = "/1220000/retrieveTrifFxrtInfo/getRetrieveTrifFxrtInfo";

    // 환율 정보 파라미터 값
    String CURRENCY_SERVICE_VALUE = "Ku5qfW/3nMsFEByRJWl8Y6lXrw3YdUInk3ehIViSXKaa2CvbD00XJJYme/O5FbLWFheHgKQJZi257JyUG9o7SA==";
    String APPLY_BGN_DT_VALUE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));   // 조회 년/월/일
    String WEEK_FXRT_TPCD_VALUE = "1";  // 수출: 1,  수입 : 2

    String APPLY_BGN_DT_KEY = "aplyBgnDt={applyBgnDt}";
    String WEEK_FXRT_TPCD_KEY = "weekFxrtTpcd={weekFxrtTpcd}";  // 수출: 1,  수입 : 2


    String CURRENCY_PARAMS = "?" + SERVICE_KEY + "&" + APPLY_BGN_DT_KEY + "&" + WEEK_FXRT_TPCD_KEY;


    String CURRENCY_API = BASE_DIR + CURRENCY_API_URL + CURRENCY_PARAMS;
}
