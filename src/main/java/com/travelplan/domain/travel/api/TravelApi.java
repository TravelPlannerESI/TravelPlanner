package com.travelplan.domain.travel.api;
import com.travelplan.domain.travel.dto.*;
import com.travelplan.domain.travel.service.TravelService;
import com.travelplan.domain.travel.web.repository.CustomTravelRepository;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.config.webconfig.annotation.OauthUser;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TravelApi {

    private final TravelService travelService;
    private final CustomTravelRepository customTravelRepository;


    @PostMapping("/api/v1/travel")
    public ResponseEntity<ResponseData> travelSave(@Validated @RequestBody TravelFormDto travelFormDto,@OauthUser SessionUser sessionUser) {
        TravelDto travelDto = travelService.addTravel(travelFormDto,sessionUser.getEmail());
        ResponseData<TravelDto> resData = new ResponseData<>(travelDto,ADD.getSuccessCode(), ADD.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    @GetMapping("/api/v1/travel")
    public ResponseEntity<ResponseData> travelList(@OauthUser SessionUser sessionUser , Pageable pageable) {
        Page<TravelDto> pageResult = customTravelRepository.findByTravelInMemberOrderByDesc(sessionUser.getEmail(), pageable);
        ResponseData<Page<TravelDto>> resData = new ResponseData<>(pageResult,SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    /**
     * ?????? ????????? ?????? ?????? ?????? - ??????(YES) / ??????(NO)
     * ???????????? EMPTY
     *
     * ???????????????????????? ????????? data
     *  - joinStatus : YES || NO
     *
     * @param inviteCode - ?????? ??????
     * @param email - sessionUser - ????????? ?????? ?????????
     * @param joinResult - ?????? / ????????? ?????? ???
     */
//    public void responseInvitation(@PathVariable("inviteCode") String inviteCode, @OauthUser SessionUser sessionUser) {
//        travelService.updateJoinStatus(inviteCode, sessionUser.getEmail());
    @PutMapping("/api/v1/travel/{inviteCode}/response")
    public ResponseEntity<ResponseData> responseInvitation(@PathVariable("inviteCode") String inviteCode,
                                                           String email, @RequestBody TravelJoinResultResponseDto joinResult) {

        // ?????? ???????????? ????????? email -> sessionUser??? ?????? ??????
        String loginUserEmail = email;
        travelService.updateJoinStatus(inviteCode, new TravelJoinResultDto(joinResult.getJoinStatus(), loginUserEmail));


        return new ResponseEntity<>(new ResponseData(RESPONSE.getSuccessCode(),
                                                     createResponseMsg(joinResult.getJoinStatus(), RESPONSE.getSuccessMessage()))
                                    , HttpStatus.CREATED);
    }

    /**
     * ?????? ????????? ????????? ????????? ???????????? ?????? ?????????
     *
     * ???????????????????????? ????????? data
     *  - joinStatus : EMPTY
     *  - email : ?????? ????????? ?????? ?????? member email
     *
     * @param inviteCode - ?????? ??????
     * @param joinResult - joinStatusCode, email
     */
    @PutMapping("/api/v1/travel/{inviteCode}/resend")
    public ResponseEntity<ResponseData> resendInvitation(@PathVariable("inviteCode") String inviteCode, @RequestBody TravelJoinResultResendDto joinResult) {

        // ????????? ????????? ?????? ????????? email
        travelService.updateJoinStatus(inviteCode, new TravelJoinResultDto(joinResult));

        return new ResponseEntity<>(new ResponseData(RESEND.getSuccessCode(), RESEND.getSuccessMessage()), HttpStatus.CREATED);
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


    private String createResponseMsg(JoinStatus joinStatus, String successMsg) {
        StringBuilder msg = new StringBuilder();
        String code = joinStatus.name();
        if (JoinStatus.YES.name().equals(code)) msg.append("?????? ").append(successMsg);
        else if (JoinStatus.NO.name().equals(code)) msg.append("?????? ").append(successMsg);

        return msg.toString();
    }

}
