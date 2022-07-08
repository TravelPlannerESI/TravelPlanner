package com.travelplan.domain.travel.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CountryRepository countryRepository;
    private final PlanService planService;

    @Transactional
    public TravelDto addTravel(TravelFormDto travelFormDto) {
        String inviteCode = UUID.randomUUID().toString();

        Country country = countryRepository.findByCovid(travelFormDto.getCountryIsoAlp2())
                .orElseThrow(NoSuchElementException::new);

        Travel travel = new Travel(travelFormDto);
        travel.addCountry(country);
        travel.saveInviteCode(inviteCode);

        // Spring Data Jpa 사용 시 save 변경필요
        travelRepository.save(travel);
        TravelDto travelDto = new TravelDto(travelFormDto);
        travelDto.setInviteCode(inviteCode);

        // 날짜 별 plan 추가
        planService.addPlan(travel, travel.getStartDate(), travel.getStartDate());

        return travelDto;
    }


}
