package com.travelplan.domain.exchangerate.repository;

import com.travelplan.domain.exchangerate.domain.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
}
