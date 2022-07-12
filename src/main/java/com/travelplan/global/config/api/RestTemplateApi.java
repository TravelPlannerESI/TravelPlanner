package com.travelplan.global.config.api;

import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;
import com.travelplan.domain.covid.domain.Covid;
import com.travelplan.domain.covid.repository.CovidRepository;
import com.travelplan.domain.covid.util.CovidApiTemplate;
import com.travelplan.domain.covid.web.service.CovidWebService;
import com.travelplan.global.config.api.constant.RestTemplateConst;
import com.travelplan.global.config.api.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestTemplateApi implements ApplicationListener<ContextRefreshedEvent> {

    private final CovidRepository covidRepository;
    private final CovidWebService covidWebService;
    private final CountryRepository countryRepository;

    // 호출 시 참조하는 인스턴스(캐싱 Data)
    public static List<CountryFormDto> countryFormList = null;
    public static List<CountryCovidInfoForm> countryFormListWithCoordinate = null;
    public static LocalDate lastUpdateDate = null;

    private static boolean INSERT = true;
    private static boolean UPDATE = false;

    /**
     *  Spring 컨텍스트의 초기화가 완료된 후 실행
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 데이터가 존재하는지 확인
        if (covidRepository.countTotalBy() == 0) {
            // 없다면 신규(최초) 추가
            mergeCovidData(INSERT);
            return;
        }

        // 당일 기준으로 삽입한 데이터가 있는지 확인
        if (hasCurrentInsertData()) {
            lastUpdateDate = LocalDate.now();
            // 존재한다면 해당 데이터 불러와 countryFormList에 대입
            countryFormList = covidRepository.findAllByOrderByCountryNmAsc().stream()
                    .map(CountryFormDto::new)
                    .collect(Collectors.toList());

            countryFormListWithCoordinate = countryRepository.selectCountryInfo();
            return;
        }

        // 해당 날짜의 삽입된 데이터가 없다면 (업데이트)
        mergeCovidData(UPDATE);

//        CoordinateDto travelMakerDto = callApi(RestTemplateConst.COORDINATE_API, HttpMethod.POST, request, CoordinateDto.class);
//        log.info("combineWithCoordinate size = {}", CountryWithCoordinateFormDto.of(warningDto, pcrDto, travelMakerDto).size());
    }

    /**
     * covid 정보를 insert/update 하는 메서드
     * @param isInsert true : insert, false : update
     */
    private void mergeCovidData(boolean isInsert) {
        // 요청 생성
        HttpEntity request = getJsonRequest();

        new CovidApiTemplate(covidRepository, countryRepository) {
            @Override
            protected void logic() {
                WarningDto warningDto = callApi(RestTemplateConst.WARNING_API, HttpMethod.GET, request, WarningDto.class, getFullParam());
                PcrDto pcrDto = callApi(RestTemplateConst.PCR_API, HttpMethod.GET, request, PcrDto.class, getFullParam());

                countryFormList = CountryFormDto.of(warningDto, pcrDto);
                if (isInsert) {
                    covidRepository.saveAll(convertCovidEntityList(countryFormList));
                } else {
                    covidWebService.updateAll(convertCovidEntityMap(countryFormList));
                }

                countryFormListWithCoordinate = countryRepository.selectCountryInfo();
            }
        }.proceed();
    }

    /**
     * 당일 기준에 삽입 된 데이터가 있는지 확인하는 메서드
     * @return boolean
     */
    private boolean hasCurrentInsertData() {
        Covid result = covidRepository.findFirstByOrderByLastModifiedDate();

        // 조건 : 오늘의 데이터가 있는지
        if (result != null && isCurrentDate(result.getLastModifiedDate())) {
            return true;
        }

        return false;
    }

    /**
     * 넘어온 파라미터가 오늘인지 확인하는 메서드
     * @param other
     * @return boolean
     */
    private boolean isCurrentDate(LocalDate other) {
        return LocalDate.now().isEqual(other);
    }

    private List<Covid> convertCovidEntityList(List<CountryFormDto> countryFormList) {
        List<Covid> covidList = new ArrayList<>();
        for (CountryFormDto dto : countryFormList) {
            covidList.add(new Covid(dto));
        }

        return covidList;
    }

    private Map<String, Covid> convertCovidEntityMap(List<CountryFormDto> countryFormList) {
        Map<String, Covid> covidMap = new HashMap<>();
        for (CountryFormDto dto : countryFormList) {
            covidMap.put(dto.getCountryIsoAlp2(), new Covid(dto));
        }

        return covidMap;
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
    private <T> T callApi(String url,
                          HttpMethod httpMethod,
                          HttpEntity request,
                          Class<T> dtoClass,
                          String... params) {

        RestTemplate restTemplate = new RestTemplate();

        T contents = restTemplate.exchange(
                url,
                httpMethod,
                request,
                dtoClass,
                params
        ).getBody();

        return contents;
    }

    private String[] getFullParam() {
        return new String[]{RestTemplateConst.SERVICE_KEY_VALUE, RestTemplateConst.RETURN_TYPE_VALUE,
                RestTemplateConst.NUM_OF_ROWS_VALUE, RestTemplateConst.PAGE_NO_VALUE};
    }

}
