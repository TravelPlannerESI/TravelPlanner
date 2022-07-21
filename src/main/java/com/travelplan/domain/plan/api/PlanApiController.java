package com.travelplan.domain.plan.api;

import com.travelplan.domain.plan.dto.PlanDto;
import com.travelplan.domain.plan.dto.PlanResponseDto;
import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.global.utils.responsedto.ResponseData;
import com.travelplan.global.utils.responsedto.constant.ResponseConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PlanApiController {

    private final PlanService planService;

    @GetMapping("/{travelId}/plan")
    public ResponseEntity<ResponseData<PlanResponseDto>> travelPlans(@PathVariable("travelId") int travelId) {
        PlanResponseDto plans = planService.findPlans(travelId);

        return new ResponseEntity<>(new ResponseData<>(plans, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage()), HttpStatus.OK);
    }

}
