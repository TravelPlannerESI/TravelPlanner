package com.travelplan.domain.covid.web.service;

import com.travelplan.domain.covid.domain.Covid;
import com.travelplan.domain.covid.repository.CovidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CovidWebService {

    private final CovidRepository covidRepository;

    @Transactional
    public void updateAll(Map<String, Covid> covidMap) {
        List<Covid> covidList = covidRepository.findAll();

        for (Covid covid : covidList) {
            Covid newCovid = covidMap.get(covid.getCountryIsoAlp2());
            covid.update(newCovid);
        }

    }

}
