package com.travelplan.domain.covid.util;

import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.covid.repository.CovidRepository;
import com.travelplan.global.config.api.RestTemplateApi;
import com.travelplan.global.config.api.dto.CountryFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class CovidApiTemplate {

    private final CovidRepository covidRepository;
    private final CountryRepository countryRepository;

    public void proceed() {
        try {
            logic();
        } catch (Exception e) {
            log.info(">> [RestTemplate] CovidApi Error");
            log.error("error = {}", e.getStackTrace());
            log.info(">> [search before data]");
            RestTemplateApi.countryFormList = covidRepository.findAllByOrderByCountryNmAsc().stream()
                    .map(CountryFormDto::new)
                    .collect(Collectors.toList());
            RestTemplateApi.countryFormListWithCoordinate = countryRepository.selectCountryInfo();
        }
    }

    protected abstract void logic();


}
