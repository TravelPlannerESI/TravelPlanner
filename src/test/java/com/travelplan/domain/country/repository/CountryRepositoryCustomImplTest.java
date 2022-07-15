package com.travelplan.domain.country.repository;

import com.travelplan.domain.country.repository.custom.CountryRepositoryCustomImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@WithMockUser("USER")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@Rollback
class CountryRepositoryCustomImplTest {


    @PersistenceContext
    EntityManager em;

    @Autowired
    CountryRepositoryCustomImpl repository;

    @Autowired
    MockMvc mvc;

    @Test
    public void test() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/country"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}