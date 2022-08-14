package com.travelplan.global.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplan.domain.travel.dto.TravelInviteDto;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.message.dto.MessageDto;
import lombok.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class MqController {
    private final SimpMessagingTemplate template;
    private static ConcurrentHashMap<Integer, List<AccMember>> curAccMap = new ConcurrentHashMap<>();

    public void send(SessionUser sessionUser, List<String> membersEmail,String travelName,Integer travelId) {
        try {
            TravelInviteDto travelInviteDto = new TravelInviteDto(sessionUser.getName(), travelName, travelId, sessionUser.getPicture());
            MessageDto<TravelInviteDto> messageDto = new MessageDto<>(travelInviteDto,true);
            membersEmail.forEach(members -> {
                String sub = makeSubStr(members);
                template.convertAndSend(sub, messageDto);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void accessPlan(SessionUser user){
        Integer travelId = user.getCurrentTravelId();
        AccMember accMember = new AccMember(user);

        if(!curAccMap.containsKey(travelId)){
            List<AccMember> notExistList = new ArrayList<>();
            notExistList.add(accMember);
            curAccMap.put(travelId, notExistList);
            MessageDto<List<AccMember>> messageDto = new MessageDto<>(notExistList,false);
            template.convertAndSend(makeSubStr(user.getEmail()),messageDto);
        }else {
            List<AccMember> existList = curAccMap.get(travelId);
            existList.add(accMember);
            existList.forEach(members -> {
                String sub = makeSubStr(members.getEmail());
                MessageDto<List<AccMember>> messageDto = new MessageDto<>(existList,false);
                template.convertAndSend(sub, messageDto);
            });
        }
    }

    public void disconnectedPlan(SessionUser user){
        Integer travelId = user.getCurrentTravelId();
        List<AccMember> accMembers = curAccMap.get(travelId);
        int size = accMembers.size();
        for (int i = 0; i < size; i++) {
            String accEmail = accMembers.get(i).getEmail();
            String curEmail = user.getEmail();
            if(accEmail.equals(curEmail)) {
                accMembers.remove(i);
                return;
            }
        }
    }


    private String makeSubStr(String email){
        return String.format("/sub/toast/%s",email);
    }


    @Getter
    @Setter
    static class AccMember{
        public AccMember(SessionUser sessionUser) {
            this.email = sessionUser.getEmail();
            this.name = sessionUser.getName();
            this.picture = sessionUser.getPicture();
        }

        private String email;
        private String name;
        private String picture;
    }




}
