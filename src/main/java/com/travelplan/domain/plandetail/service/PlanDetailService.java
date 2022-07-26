package com.travelplan.domain.plandetail.service;

import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.domain.plandetail.dto.PlanDetailAddFormDto;
import com.travelplan.domain.plandetail.dto.PlanDetailListDto;
import com.travelplan.domain.plandetail.exception.NotExistPlanException;
import com.travelplan.domain.plandetail.repository.PlanDetailRepository;
import com.travelplan.domain.plandetail.web.repository.PlanDetailRepositoryCustom;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanDetailService {

    private final MemberRepositoryCustom memberRepositoryCustom;
    private final PlanDetailRepository planDetailRepository;
    private final PlanDetailRepositoryCustom planDetailRepositoryCustom;
    private final PlanRepository planRepository;
    private final TravelRepository travelRepository;


    @Transactional
    public List<PlanDetailListDto> findPlanDetail(Integer travelId) {
        List<Plan> planList = planRepository.findByTravel(getTravel(travelId));
        return planDetailRepositoryCustom.findByPlans(planList).stream()
                .map(PlanDetailListDto::new)
                .collect(Collectors.toList());
    }

    private Travel getTravel(Integer travelId) {
        return travelRepository.findById(travelId).orElseThrow(() -> new IllegalStateException("잘못된 요청입니다."));
    }

    @Transactional
    public void addPlanDetail(PlanDetailAddFormDto planDetailAddFormDto, String userEmail) {
        validateExistPlan(planDetailAddFormDto.getTravelId(), userEmail);

        planDetailRepository.save(getPlanDetailToDto(planDetailAddFormDto));
    }

    /**
     * detailPlan 을 등록하기 전
     * Travel 에 본인이 속해 있는지 확인
     * @param travelId
     * @param userEmail
     */
    private void validateExistPlan(Integer travelId, String userEmail) {
        if(!memberRepositoryCustom.existByTravelIdAndUserId(travelId, userEmail)) {
            throw new NotExistPlanException();
        }
    }

    private PlanDetail getPlanDetailToDto(PlanDetailAddFormDto planDetailAddFormDto) {
        PlanDetail planDetail = new PlanDetail(planDetailAddFormDto);
        Plan plan = planRepository.findById(planDetailAddFormDto.getPlanId()).orElseThrow(()-> new IllegalStateException("잘못된 요청입니다."));
        planDetail.changePlan(plan);
        return planDetail;
    }

}
