package com.travelplan.domain.country.repository;

import com.travelplan.domain.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    Optional<Country> findByCountryId(Integer id);
}
