package com.travelplan.domain.travel.repository;

import com.travelplan.domain.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel,Integer> {
    List<Travel> findByCreatedBy(String userId);
    List<Travel> findByCreatedByOrderByStartDateDesc(String userId);
}
