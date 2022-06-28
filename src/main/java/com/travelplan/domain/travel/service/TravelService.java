package com.travelplan.domain.travel.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public TravelDto addTravel(TravelFormDto travelFormDto) {
        String inviteCode = UUID.randomUUID().toString();

        // 국가코드 추가 후 국가코드로 가져오는 로직으로 변경이 필요하다.
        Country country = countryRepository.findByCountryId(travelFormDto.getCountryDto().getCountryId())
                .orElseThrow(NoSuchElementException::new);
        Travel travel = new Travel(travelFormDto);
        travel.addCountry(country);
        travel.saveInviteCode(inviteCode);

        // Spring Data Jpa 사용 시 save 변경필요
        travelRepository.save(travel);
        TravelDto travelDto = new TravelDto(travelFormDto);
        travelDto.setInviteCode(inviteCode);

        return travelDto;
    }


}
