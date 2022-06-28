package com.travelplan.global.config.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class TravelMakerApiTest {

    private RestTemplate restTemplate = new RestTemplate();


    @Test
    void 모든_정보를_가져온다() {

        String url = "https://www.travelmakerkorea.com/api/newsList";

        HttpEntity<Object> headers = setHttpHeaders();


        ResponseEntity<TravelMakerDTO> exchange = restTemplate.exchange(url, HttpMethod.POST, headers, TravelMakerDTO.class);

        exchange.getBody().getResult().stream().forEach(System.out::println);
    }

    private HttpEntity<Object> setHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

         return new HttpEntity<>(headers);
    }


    @Data
    static class TravelMakerDTO {

        List<TravelMakerDetailDTO> result;
    }

    @Data
    static class TravelMakerDetailDTO {
        private String country_code;
        private String country_name;
        private String country_EN_name;
        private String lat;
        private String lng;
//        private String memo;
    }





}
