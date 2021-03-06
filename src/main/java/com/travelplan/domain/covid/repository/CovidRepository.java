package com.travelplan.domain.covid.repository;

import com.travelplan.domain.covid.domain.Covid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CovidRepository extends JpaRepository<Covid, String> {

    Covid findFirstByOrderByLastModifiedDate();

    List<Covid> findAllByOrderByCountryNmAsc();

    Integer countTotalBy();
}
