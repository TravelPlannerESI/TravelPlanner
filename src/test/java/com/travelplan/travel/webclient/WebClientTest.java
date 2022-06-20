package com.travelplan.travel.webclient;

import com.travelplan.dto.ApiDto;
import com.travelplan.travel.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientTest {

    @Autowired
    TestRepository testRepository;

    @Test
    void test2() {

        // 여행 유/무 API
        String url = "http://apis.data.go.kr/1262000/TravelAlarmService2/getTravelAlarmList2?serviceKey=9Q4zVRGrJSpT04lu40y11GsFga4BWVpdabVl3UpFZUwAo4YblPW46yyYuWgLPyZ/31Le/wAjM4SnQaTIZtZXvw==&returnType=JSON&numOfRows=100&pageNo=2";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();

        // set `Content-Type` and `Accept` headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // example of custom header
        headers.set("X-Request-Source", "Desktop");

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // make an HTTP GET request with headers
        ResponseEntity<ApiDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                ApiDto.class,
     1 // uri 파라미터 값
        );

        System.out.println("response = " + response);


    }



}
