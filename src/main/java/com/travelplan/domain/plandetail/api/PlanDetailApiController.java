package com.travelplan.domain.plandetail.api;

import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.domain.plandetail.dto.PlanDetailAddFormDto;
import com.travelplan.domain.plandetail.dto.PlanDetailListDto;
import com.travelplan.domain.plandetail.dto.PlanDetailModifyFormDto;
import com.travelplan.domain.plandetail.repository.PlanDetailRepository;
import com.travelplan.domain.plandetail.service.PlanDetailService;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.config.webconfig.annotation.OauthUser;
import com.travelplan.global.utils.responsedto.ResponseData;
import com.travelplan.global.utils.responsedto.constant.ResponseConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PlanDetailApiController {

    private final PlanDetailService planDetailService;
    private final PlanDetailRepository planDetailRepository;
    private final PlanRepository planRepository;

    @GetMapping("/planDetail")
    public ResponseEntity<ResponseData> planDetailList(@OauthUser SessionUser user) {
        return ResponseEntity.ok(new ResponseData<>(planDetailService.findPlanDetail(user.getCurrentTravelId()), SEARCH.getSuccessCode(), SEARCH.getSuccessMessage()));
    }

    @GetMapping("/planDetail/{planId}")
    public ResponseEntity<ResponseData> planDetailDaysList(@PathVariable Integer planId) {
        return ResponseEntity.ok(new ResponseData<>(planDetailService.findPlanDetailDays(planId), SEARCH.getSuccessCode(), SEARCH.getSuccessMessage()));
    }

    @PostMapping("/planDetail")
    public ResponseEntity<ResponseData> planDetailAdd(@OauthUser SessionUser user, @RequestBody PlanDetailAddFormDto planDetailAddFormDto) {
        planDetailService.addPlanDetail(planDetailAddFormDto, user.getEmail());

        return new ResponseEntity(new ResponseData<>(ADD.getSuccessCode(), ADD.getSuccessMessage()), HttpStatus.CREATED);
    }

    @PutMapping("/planDetail/{planDetailId}")
    public ResponseEntity<ResponseData> planDetailModify(@RequestBody PlanDetailModifyFormDto planDetailModifyFormDto,
                                                         @PathVariable Integer planDetailId) {
        planDetailService.modifyPlanDetail(planDetailModifyFormDto, planDetailId);
        return new ResponseEntity(new ResponseData<>(UPDATE.getSuccessCode(), UPDATE.getSuccessMessage()), HttpStatus.CREATED);
    }

}
