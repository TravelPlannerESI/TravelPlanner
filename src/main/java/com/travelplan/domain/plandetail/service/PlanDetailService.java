package com.travelplan.domain.plandetail.service;

import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.domain.plandetail.dto.*;
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
    public PlanResponseDto findPlanDetail(Integer travelId) {
        Travel travel = getTravel(travelId);
        List<Plan> planList = planRepository.findByTravel(travel);

        // plan list 조회
        List<PlanListDto> plans = planList.stream()
                .map(PlanListDto::new)
                .collect(Collectors.toList());

        // plan detail list 조회
        List<PlanDetailListDto> planDetails = planDetailRepositoryCustom.findByPlans(planList);

        return new PlanResponseDto(travel.getTravelName(), plans, planDetails);
    }

    public List<PlanDetailListDto> findPlanDetailDays(Integer planId) {
        return planDetailRepositoryCustom.finByPlanId(planId);
    }

    private Travel getTravel(Integer travelId) {
        return travelRepository.findById(travelId).orElseThrow(() -> new IllegalStateException("잘못된 요청입니다."));
    }

    @Transactional
    public void addPlanDetail(PlanDetailAddFormDto planDetailAddFormDto, String userEmail) {
        validateExistPlan(planDetailAddFormDto.getTravelId(), userEmail);

        planDetailRepository.save(getPlanDetailToDto(planDetailAddFormDto));
    }

    @Transactional
    public void modifyPlanDetail(PlanDetailModifyFormDto planDetailModifyFormDto, Integer planDetailId) {
        PlanDetail planDetail = planDetailRepository.findById(planDetailId).orElseThrow(() -> new IllegalStateException("잘못된 요청입니다."));
        planDetail.updatePlanDetail(planDetailModifyFormDto);
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
