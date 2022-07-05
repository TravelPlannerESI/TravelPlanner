package com.travelplan.domain.country.service;

import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;
import com.travelplan.domain.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public List<CountryCovidInfoForm> findAllCountryInfo() {
        return countryRepository.selectCountryInfo();
    }


}
