package com.travelplan.domain.plandetail.service;

import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.plandetail.domain.PlanDetail;
import com.travelplan.domain.plandetail.dto.PlanDetailAddFormDto;
import com.travelplan.domain.plandetail.exception.NotExistPlanException;
import com.travelplan.domain.plandetail.repository.PlanDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanDetailService {

    private final MemberRepositoryCustom memberRepositoryCustom;
    private final PlanDetailRepository planDetailRepository;

    @Transactional
    public void addPlanDetail(PlanDetailAddFormDto planDetailAddFormDto, String userEmail) {
        validateExistPlan(planDetailAddFormDto.getTravelId(), userEmail);

        planDetailRepository.save(new PlanDetail(planDetailAddFormDto));
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

}
