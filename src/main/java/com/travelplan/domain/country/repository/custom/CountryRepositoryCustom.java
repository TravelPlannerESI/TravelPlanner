package com.travelplan.domain.country.repository.custom;

import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;

import java.util.List;

public interface CountryRepositoryCustom {

    List<CountryCovidInfoForm> selectCountryInfo();

}
