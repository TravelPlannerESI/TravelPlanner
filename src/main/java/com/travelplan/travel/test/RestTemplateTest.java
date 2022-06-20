package com.travelplan.travel.test;

import com.travelplan.dto.ApiDto;
import com.travelplan.dto.ContentData;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class RestTemplateTest {

    public static final String url = "http://apis.data.go.kr/1262000/TravelAlarmService2/getTravelAlarmList2?serviceKey={serviceKey}&returnType={returnType}&numOfRows={numOfRows}&pageNo={pageNo}";

    public static final String serviceKey = "9Q4zVRGrJSpT04lu40y11GsFga4BWVpdabVl3UpFZUwAo4YblPW46yyYuWgLPyZ/31Le/wAjM4SnQaTIZtZXvw==";
    public static final String returnType = "JSON";
    public static final String numOfRows = "200";
    public static final String pageNo = "1";

    public static Optional<ApiDto> apiDto = Optional.ofNullable(null);

    @PostConstruct
    public void init() {

        RestTemplate restTemplate = new RestTemplate();

        // 해더 생성
        HttpHeaders headers = new HttpHeaders();

        // ContentType, Accept 설정
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ApiDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                ApiDto.class,
                serviceKey,
                returnType,
                numOfRows,
                pageNo
        );

        List<ContentData> data = response.getBody().getData();

        data.stream().forEach(System.out::println);

        apiDto = Optional.ofNullable(response.getBody());

    }


}
