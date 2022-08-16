package com.travelplan.domain.exchangerate.web.service;

import com.travelplan.domain.exchangerate.domain.ExchangeRate;
import com.travelplan.domain.exchangerate.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeRateWebService {

    private final ExchangeRateRepository repository;

    public void updateAll(Map<String, ExchangeRate> exchangeRateMap) {
        List<ExchangeRate> list = repository.findAll();

        list.forEach(rate -> rate.update(exchangeRateMap.get(rate.getCntySgn())));
    }
}

