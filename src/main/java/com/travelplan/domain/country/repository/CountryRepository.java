package com.travelplan.domain.country.repository;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.custom.CountryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Integer>, CountryRepositoryCustom {
    Optional<Country> findByCountryName(String code);
}
