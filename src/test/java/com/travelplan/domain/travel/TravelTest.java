package com.travelplan.domain.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.web.dto.CountryDto;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.travel.web.repository.CustomTravelRepository;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
class TravelTest {

    public static TravelFormDto travelFormDto;

    public static final String EMAIL = "ats3059@gmail.com";
    public static final String INVITE_CODE = UUID.randomUUID().toString();
    public static final LocalDate NOW = LocalDate.now();
    public static final LocalDate END_DATE = NOW.plusDays(5);

    static {
        travelFormDto = new TravelFormDto();
        travelFormDto.setTravelName("오사카 먹방여행");
        travelFormDto.setStartDate(NOW);
//        travelFormDto.setLastModifiedDate(NOW);
//        travelFormDto.setLastModifiedName(EMAIL);
        travelFormDto.setEndDate(END_DATE);
//        travelFormDto.setCreateUserId(EMAIL);
        CountryDto countryDto = new CountryDto();
        countryDto.setCountryId(1);
        countryDto.setCountryName("일본");
//        travelFormDto.setCountryDto(countryDto);
    }

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TravelRepository travelRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomTravelRepository customTravelRepository;

    @Test
    public void 여행을_계획하고_저장한다() {
        Country getCountry = em.createQuery("select c from Country c where c.countryName = :name", Country.class)
                .setParameter("name", "일본")
                .getSingleResult();

        Travel travel = new Travel(travelFormDto);
        travel.saveInviteCode(INVITE_CODE);
        travel.addCountry(getCountry);

        em.persist(travel);
        em.flush();
        em.clear();

        List<Travel> result = travelRepository.findByCreatedBy("ats3059@naver.com");

        Country validCountry = em.createQuery("select c from Country c where c.countryName = :name", Country.class)
                .setParameter("name", "일본")
                .getSingleResult();

        for (Travel in : result) {
            if ("오사카 먹방여행".equals(in.getTravelName())) {
                Assertions.assertThat(in.getCountry()).isEqualTo(validCountry);
                Assertions.assertThat(in.getInviteCode()).isEqualTo(INVITE_CODE);
//                Assertions.assertThat(in.getCreateUserId()).isEqualTo(EMAIL);
            }
        }
    }

    @Test
    public void 여행을_계획하고_저장한다_로직_테스트() throws Exception {

        String content = objectMapper.writeValueAsString(travelFormDto);

        mvc.perform(post("/api/v1/travel")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}