package com.travelplan.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplan.domain.zexceptionTest.TestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc
class GlobalControllerAdviceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Http GET - Success Response")
    public void get_성공() throws Exception {

        mvc.perform(get("/api/v1/tmp")
                .param("aaa", "hello"))
                .andExpect(status().isOk())
                .andDo(print());
    }


//    @Test
    @DisplayName("Http GET - Exception Response")
    public void get_실패_500_예외발생() throws Exception {

        mvc.perform(get("/api/v1/tmp")
                .param("aaa", "ex"))
                .andReturn();
    }


    @Test
    @DisplayName("Http POST - Success Response")
    public void post_성공() throws Exception {

        String content = objectMapper.writeValueAsString(new TestDTO("아이디", "이름", "이메일", "전화번호호"));

        mvc.perform(post("/api/v1/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Http POST - Exception Response")
    public void post_실패_400_예외발생() throws Exception {

        String content = objectMapper.writeValueAsString(new TestDTO("아이디", "", "", "123456789101112"));

        mvc.perform(post("/api/v1/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}