package com.travelplan.domain.country;

import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;
import com.travelplan.global.config.api.RestTemplateApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HtmlEscapeTest {


    @Test
    void escapeTest() {
        List<CountryCovidInfoForm> list = RestTemplateApi.countryFormListWithCoordinate;

        list.stream()
                .filter((data) -> "TM".equals(data.getCountryIsoAlp2()))
                .forEach(x -> System.out.println(x.getTxtOriginCn()));
    }

}
