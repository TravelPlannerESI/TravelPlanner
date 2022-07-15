package com.travelplan.domain.country.api;

import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;
import com.travelplan.global.config.api.RestTemplateApi;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CountryController {


    @GetMapping("/api/v1/country")
    public ResponseData<List<CountryCovidInfoForm>> countryList() {
        List<CountryCovidInfoForm> list = RestTemplateApi.countryFormListWithCoordinate;

        return new ResponseData<>(list, "A1", "성공");
    }

}
