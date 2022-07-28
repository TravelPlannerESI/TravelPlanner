package com.travelplan.domain.setting.api;

import com.travelplan.domain.setting.service.SettingService;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelCountryDto;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelMembers;
import com.travelplan.domain.travel.dto.TravelJoinResultDto;
import com.travelplan.domain.travel.dto.TravelJoinResultResendDto;
import com.travelplan.domain.travel.dto.TravelJoinResultResponseDto;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.domain.user.web.repository.UserRepositoryCustom;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@Slf4j
@RequestMapping("/api/v1/setting")
@RequiredArgsConstructor
@RestController
public class SettingApiController {

    private final SettingService settingService;
    private final UserRepositoryCustom userRepositoryCustom;

    // 내가 만든 여행

    /**
     * 로그인한 사용자가 만든 여행 목록 조회
     * @return
     */
    @GetMapping("/travel/made")
    public ResponseEntity<ResponseData<Page<SettingTravelCountryDto>>> createdTravel(@OauthUser SessionUser sessionUser, Pageable pageable) {
        Page<SettingTravelCountryDto> travelList = settingService.findTravelsByLoginUser(sessionUser.getEmail(), pageable);

        ResponseData<Page<SettingTravelCountryDto>> resData = new ResponseData<>(travelList, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());

        return ResponseEntity.ok(resData);
    }


    /**
     * 여행에 초대된 사용자들
     * @param travelId - 해당 여행 id
     * @return
     */
    @GetMapping("/travel/members/{travelId}")
    public ResponseEntity<ResponseData<List<SettingTravelMembers>>> memberList(@PathVariable int travelId) {
        List<SettingTravelMembers> members = settingService.findTravelMembers(travelId);
        ResponseData<List<SettingTravelMembers>> resData = new ResponseData<>(members, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }


    /**
     * 선택된 여행과 관련이 없는 사용자들을 조회한다.
     * @param userEmail - email Keyword
     * @param invitedMembers - 여행에 포함되어있는 사용자
     * @return
     */
    @GetMapping("/travel/users/{userEmail}")
    public ResponseEntity<ResponseData> userEmailFind(@PathVariable String userEmail, @RequestParam List<String> invitedMembers) {
        List<String> emailList = userRepositoryCustom.findByEmailExceptInvited(userEmail, invitedMembers);
        ResponseData<List<String>> resData = new ResponseData<>(emailList, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    /**
     * 새로운 사용자를 초대한다.
     * @param travelId - 해당 여행의 id
     * @param newEmails - 새롭게 추가될 사용자의 email
     * @return
     */
    @PostMapping("/travel/members/add/{travelId}")
    public ResponseEntity<ResponseData> addNewMembers(@PathVariable int travelId, @RequestBody List<String> newEmails) {
        settingService.addMembers(travelId, newEmails);

        return new ResponseEntity<>(new ResponseData<>(ADD.getSuccessCode(), ADD.getSuccessMessage()), HttpStatus.CREATED);
    }



    /**
     * 여행 팀장이 초대를 거절한 멤버에게 요청 재전송
     *
     * 클라이언트로부터 필요한 data
     *  - joinStatus : EMPTY
     *  - email : 초대 요청을 다시 보낼 member email
     *
     * @param inviteCode - 초대 코드
     * @param joinResult - joinStatusCode, email
     */
    @PutMapping("/travel/{inviteCode}/resend")
    public ResponseEntity<ResponseData> resendInvitation(@PathVariable("inviteCode") String inviteCode, @RequestBody TravelJoinResultResendDto joinResult) {

        // 재전송 요청을 보낼 멤버의 email
        settingService.updateJoinStatus(inviteCode, new TravelJoinResultDto(joinResult));

        return new ResponseEntity<>(new ResponseData(RESEND.getSuccessCode(), RESEND.getSuccessMessage()), HttpStatus.CREATED);
    }




    // 내가 만들지 않고 초대된 여행

    @GetMapping("/travel/invited")
    public ResponseEntity<ResponseData<Page<SettingTravelCountryDto>>> invitedTravel(@OauthUser SessionUser sessionUser, Pageable pageable) {
        Page<SettingTravelCountryDto> travelList = settingService.findInvitedTravels(sessionUser.getEmail(), pageable);

        ResponseData<Page<SettingTravelCountryDto>> resData = new ResponseData<>(travelList, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    /**
     * 여행 계획에 초대 받은 멤버 - 승인(YES) / 거절(NO)
     * 최초값은 EMPTY
     *
     * 클라이언트로부터 필요한 data
     *  - joinStatus : YES || NO
     *
     * @param inviteCode - 초대 코드
     * @param sessionUser - 로그인 중인 사용자
     * @param joinResult - 승인 / 거절에 대한 값
     */
    @PutMapping("/travel/{inviteCode}/response")
    public ResponseEntity<ResponseData> responseInvitation(@PathVariable("inviteCode") String inviteCode,
                                                           @OauthUser SessionUser sessionUser, @RequestBody TravelJoinResultResponseDto joinResult) {

        String loginUserEmail = sessionUser.getEmail();
        settingService.updateJoinStatus(inviteCode, new TravelJoinResultDto(joinResult.getJoinStatus(), loginUserEmail));

        return new ResponseEntity<>(new ResponseData(RESPONSE.getSuccessCode(), createResponseMsg(joinResult.getJoinStatus(), RESPONSE.getSuccessMessage()))
                                    , HttpStatus.CREATED);
    }



    private String createResponseMsg(JoinStatus joinStatus, String successMsg) {
        StringBuilder msg = new StringBuilder();
        String code = joinStatus.name();
        if (JoinStatus.YES.name().equals(code)) msg.append("승인 ").append(successMsg);
        else if (JoinStatus.NO.name().equals(code)) msg.append("거절 ").append(successMsg);

        return msg.toString();
    }

}
