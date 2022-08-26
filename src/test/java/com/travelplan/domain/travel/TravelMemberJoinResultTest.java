package com.travelplan.domain.travel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplan.domain.travel.dto.TravelJoinResultResponseDto;
import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TravelMemberJoinResultTest {

//    @Autowired
    private MockMvc mvc;

//    @Autowired
    private ObjectMapper objectMapper;


//    @Test
    @DisplayName("여행 계획에 초대 받은 멤버 - 승인(YES) / 거절(NO)")
    public void response() throws Exception {


        String val = objectMapper.writeValueAsString(new TestJoinResultDTO("YES"));
        String email = "tu4050@naver.com";
        String inviteCode = "da2f2689-6a97-46f1-bd4f-dca0e0e91069";

        mvc.perform(
                put("/api/v1/travel/{inviteCode}/response", inviteCode)
                        .param("email", email)
                        .content(val)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

//    @Test
    @DisplayName("여행 팀장이 초대를 거절한 멤버에게 요청 재전송")
    public void resend() throws Exception {


        String val = objectMapper.writeValueAsString(new TestJoinResultDTO("YES", "tu4050@naver.com"));
        String inviteCode = "da2f2689-6a97-46f1-bd4f-dca0e0e91069";

        mvc.perform(
                put("/api/v1/travel/{inviteCode}/resend", inviteCode)
                        .content(val)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }



    @Getter
    @Setter
    static class TestJoinResultDTO {
        private String joinStatus;
        private String email;

        public TestJoinResultDTO(String joinStatus) {
            this.joinStatus = joinStatus;
        }

        public TestJoinResultDTO(String joinStatus, String email) {
            this.joinStatus = joinStatus;
            this.email = email;
        }
    }
}
