package com.travelplan.domain.country.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.country.repository.dto.CountryCovidInfoForm;
import com.travelplan.domain.country.repository.dto.QCountryCovidInfoForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.travelplan.domain.country.domain.QCountry.country;
import static com.travelplan.domain.covid.domain.QCovid.covid;


@Slf4j
@Repository
public class CountryRepositoryCustomImpl implements CountryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CountryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CountryCovidInfoForm> selectCountryInfo() {

        List<CountryCovidInfoForm> fetch = queryFactory
                .select(
                        new QCountryCovidInfoForm(country, covid)
                )
                .from(country)
                .leftJoin(country.covid, covid)
                .fetch();


        return fetch;
    }
}


