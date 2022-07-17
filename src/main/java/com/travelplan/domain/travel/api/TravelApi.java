package com.travelplan.domain.travel.api;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.dto.TravelJoinResultDto;
import com.travelplan.domain.travel.service.TravelService;
import com.travelplan.domain.travel.web.repository.CustomTravelRepository;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.config.webconfig.annotation.OauthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TravelApi {

    private final TravelService travelService;
    private final CustomTravelRepository customTravelRepository;


    @PostMapping("/api/v1/travel")
    public TravelDto travelSave(@Validated @RequestBody TravelFormDto travelFormDto,@OauthUser SessionUser sessionUser) {
        TravelDto travelDto = travelService.addTravel(travelFormDto,sessionUser.getEmail());
        return travelDto;
    }

    @GetMapping("/api/v1/travel")
    public List<TravelDto> travelList(@OauthUser SessionUser sessionUser) {
        if(sessionUser!=null) return customTravelRepository.findByTravelInMemberOrderByDesc(sessionUser.getEmail());
        throw new RuntimeException("권한이 없습니다.");
    }

    /**
     * 여행 계획에 초대 받은 멤버 - 승인(YES) / 거절(NO)
     * 최초값은 EMPTY
     *
     * @param inviteCode - 초대 코드
     * @param email - sessionUser - 로그인 중인 사용자
     * @param joinResult - 승인 / 거절에 대한 값
     */
//    public void responseInvitation(@PathVariable("inviteCode") String inviteCode, @OauthUser SessionUser sessionUser) {
//        travelService.updateJoinStatus(inviteCode, sessionUser.getEmail());
    @PutMapping("/api/v1/travel/{inviteCode}/response")
    public ResponseEntity<?> responseInvitation(@PathVariable("inviteCode") String inviteCode,
                                             String email, @RequestBody TravelJoinResultDto joinResult) {
        travelService.updateJoinStatus(inviteCode, "response", email, joinResult);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 여행 팀장이 초대를 거절한 멤버에게 재전송
     * 
     * @param inviteCode - 초대 코드
     * @param joinResult - 재전송을 보낼 멤버의 email이 들어있다.
     * @return
     */
    @PutMapping("/api/v1/travel/{inviteCode}/resend")
    public ResponseEntity<?> resendInvitation(@PathVariable("inviteCode") String inviteCode, @RequestBody TravelJoinResultDto joinResult) {
        travelService.updateJoinStatus(inviteCode, "resend", joinResult.getEmail(), joinResult);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    private boolean isLogin(String email, HttpServletRequest request) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        HttpSession session = request.getSession(false);
        SessionUser user = (SessionUser)session.getAttribute("user");
        if(authenticated){
            if(user.getEmail().equals(email)) return true;
            else return false;
        }else return false;
    }


}
