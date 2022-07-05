package com.travelplan.domain.country.repository;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.custom.CountryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Integer>, CountryRepositoryCustom {
    Optional<Country> findByCountryIsoAlp2(String code);
}
