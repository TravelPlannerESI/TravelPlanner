package com.travelplan.domain.travel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplan.domain.travel.dto.TravelInviteDto;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TravelMqController {
    private final SimpMessagingTemplate template;

    public void send(SessionUser sessionUser, List<String> membersEmail,String travelName,Integer travelId) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String msg = objectMapper.writeValueAsString(new TravelInviteDto(sessionUser.getName(),travelName, travelId , sessionUser.getPicture()));
            membersEmail.forEach(members -> {
                String sub = String.format("/sub/toast/%s",members);
                template.convertAndSend(sub, msg);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



}
