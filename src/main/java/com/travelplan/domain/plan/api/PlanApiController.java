package com.travelplan.domain.plan.api;

import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PlanApiController {

}
