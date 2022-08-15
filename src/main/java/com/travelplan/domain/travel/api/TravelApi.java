package com.travelplan.domain.travel.api;

import com.travelplan.domain.travel.dto.*;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.travel.service.TravelService;
import com.travelplan.domain.travel.web.repository.CustomTravelRepository;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.config.webconfig.annotation.OauthUser;
import com.travelplan.global.message.MqController;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TravelApi {

    private final TravelService travelService;
    private final CustomTravelRepository customTravelRepository;
    private final MqController travelMqController;
    private final TravelRepository travelRepository;

    @PostMapping("/api/v1/travel")
    public ResponseEntity<ResponseData> travelSave(@Validated @RequestBody TravelFormDto travelFormDto,@OauthUser SessionUser sessionUser) {
        TravelDto travelDto = travelService.addTravel(travelFormDto,sessionUser.getEmail());
        travelMqController.send(sessionUser,travelFormDto.getMembersEmail(),travelFormDto.getTravelName(),travelDto.getTravelId());
        ResponseData<TravelDto> resData = new ResponseData<>(travelDto,ADD.getSuccessCode(), ADD.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    @GetMapping("/api/v1/travel")
    public ResponseEntity<ResponseData> travelList(@OauthUser SessionUser sessionUser, Pageable pageable) {
        Page<TravelDto> pageResult = customTravelRepository.findByTravelInMemberOrderByDesc(sessionUser.getEmail(), pageable);
        ResponseData<Page<TravelDto>> resData = new ResponseData<>(pageResult, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    @GetMapping("/api/v1/manage/travel")
    public ResponseEntity<ResponseData<TravelModifyDto>> travelManageList(@OauthUser SessionUser user) {
        ResponseData<TravelModifyDto> result = new ResponseData<>(travelService.findTravelList(user.getCurrentTravelId(), user.getEmail()),
                SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(result);
    }

    private boolean isLogin(String email, HttpServletRequest request) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        HttpSession session = request.getSession(false);
        SessionUser user = (SessionUser) session.getAttribute("user");
        if (authenticated) {
            if (user.getEmail().equals(email)) return true;
            else return false;
        } else return false;
    }

    @GetMapping("/api/v1/travel/toast")
    public ResponseEntity<ResponseData> inviteToast(@OauthUser SessionUser sessionUser) {
        List<TravelInviteDto> fetchNoInviteTravel = customTravelRepository.findByTravelMemberOrderByDesc(sessionUser.getEmail());
        ResponseData<List<TravelInviteDto>> resData = new ResponseData<>(fetchNoInviteTravel,SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }


    @GetMapping("/api/v1/travel/dashboard")
    public ResponseEntity<ResponseData<TravelCountryInfoDto>> dashboard(@OauthUser SessionUser sessionUser) {

        TravelCountryInfoDto travelCountryInfo = customTravelRepository.findTravelCountryInfo(sessionUser.getCurrentTravelId());

        ResponseData<TravelCountryInfoDto> resData = new ResponseData<>(travelCountryInfo, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

    @PutMapping("/api/v1/travel")
    public ResponseEntity<ResponseData> travelModify(@RequestBody TravelModifyFormDto dto, @OauthUser SessionUser sessionUser) {
        travelService.modifyTravel(dto, sessionUser.getCurrentTravelId(), sessionUser.getEmail());
        return null;
    }

}
