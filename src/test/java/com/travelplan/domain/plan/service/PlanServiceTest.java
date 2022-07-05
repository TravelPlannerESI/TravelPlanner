package com.travelplan.domain.plan.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.web.dto.CountryDto;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelFormDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@WithMockUser(roles = "USER")
@SpringBootTest
@Transactional
@Slf4j
class PlanServiceTest {

    @Autowired
    PlanService planService;

    @Autowired
    PlanRepository planRepository;

    public static TravelFormDto travelFormDto;

    public static final String EMAIL = "sex6969@pornhub.com";
    public static final String INVITE_CODE = UUID.randomUUID().toString();
    public static final LocalDateTime NOW = LocalDateTime.now();
    public static final LocalDateTime END_DATE = NOW.plusDays(5);

    @PersistenceContext
    EntityManager em;

    @BeforeAll
    public static void setTravelFormDto() {
        travelFormDto = new TravelFormDto();
        travelFormDto.setTravelName("오사카 먹방여행");
//        travelFormDto.setStartDate(NOW);
//        travelFormDto.setLastModifiedDate(NOW);
//        travelFormDto.setLastModifiedName(EMAIL);
//        travelFormDto.setEndDate(END_DATE);
//        travelFormDto.setCreateUserId(EMAIL);
        CountryDto countryDto = new CountryDto();
        countryDto.setCountryId(1);
        countryDto.setCountryName("일본");
//        travelFormDto.setCountryDto(countryDto);
    }

    @Test
    void addPlan() {
        Country getCountry = em.createQuery("select c from Country c where c.countryName = :name", Country.class)
                .setParameter("name", "일본")
                .getSingleResult();

        Travel travel = new Travel(travelFormDto);
        travel.saveInviteCode(INVITE_CODE);
        travel.addCountry(getCountry);

        em.persist(travel);
        em.flush();

//        planService.addPlan(travel, travel.getStartDate(), travel.getEndDate());
        em.clear();

        List<Plan> result = planRepository.findByTravel(travel);

        assertThat(result.size()).isEqualTo(6);
    }

}