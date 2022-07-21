package com.travelplan.domain.plan.service;

import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.dto.PlanDto;
import com.travelplan.domain.plan.dto.PlanResponseDto;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.plan.util.PlanDateUtil;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.global.exception.customexception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void addPlan(Travel travel, LocalDate startDate, LocalDate endDate) {
        // 두 날짜의 차이를 가져온다.
        long betweenDateCount = PlanDateUtil.getBetweenDateCount(startDate, endDate, true);
        // 시작 일과 종료 일 사이의 날짜들을 가져온다.
        Map<Integer, LocalDate> betweenDateWithDays = PlanDateUtil.getBetweenDateWithDays(startDate, endDate);

        for (long i = 0; i < betweenDateCount; i++) {
            planRepository.save(new Plan(travel, Math.toIntExact(i), betweenDateWithDays.get(Math.toIntExact(i))));
        }

    }

    @Transactional(readOnly = true)
    public PlanResponseDto findPlans(int travelId) {
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new IdNotFoundException("해당 여행 정보를 찾지 못했습니다."));

        List<Plan> findPlans = planRepository.findByTravel(travel);

        List<PlanDto> plans = findPlans.stream()
                .map((plan) -> new PlanDto(plan.getPlanId(), plan.getDays(), plan.getCurrentDay()))
                .collect(Collectors.toList());


        return new PlanResponseDto(travel.getTravelName(), plans);
    }


}
