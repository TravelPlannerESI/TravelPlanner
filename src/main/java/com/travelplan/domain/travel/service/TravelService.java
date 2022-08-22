package com.travelplan.domain.travel.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.repository.MemberRepository;
import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.plan.domain.Plan;
import com.travelplan.domain.plan.repository.PlanRepository;
import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.domain.plan.util.PlanDateUtil;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.dto.TravelModifyDto;
import com.travelplan.domain.travel.dto.TravelModifyFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.config.auth.oauth2.config.properties.GlobalProperties;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CountryRepository countryRepository;
    private final PlanService planService;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final PlanRepository planRepository;


    @Transactional
    public TravelDto addTravel(TravelFormDto travelFormDto, String email) {
        String inviteCode = UUID.randomUUID().toString();

        Country country = countryRepository.findByCountryName(travelFormDto.getCountryIsoAlp2())
                .orElseThrow(NoSuchElementException::new);

        Travel travel = new Travel(travelFormDto);
        travel.addCountry(country);
        travel.saveInviteCode(inviteCode);

        // Spring Data Jpa 사용 시 save 변경필요
        travelRepository.save(travel);

        // 멤버생성
        makeMembers(travelFormDto, travel, email);

        TravelDto travelDto = new TravelDto(travelFormDto);
        travelDto.setInviteCode(makeInviteUrl(inviteCode));

        // 날짜 별 plan 추가
        planService.addPlan(travel, travel.getStartDate(), travel.getEndDate());

        return travelDto;
    }

    public TravelModifyDto findTravelList(Integer travelId, String email) {
        TravelModifyDto findTravelDto = travelRepository.findModifyDtoByTravelId(travelId);
        findTravelDto.setMembersEmail(memberRepositoryCustom.findByTravelId(travelId));
        return findTravelDto;
    }

    @Transactional
    public void modifyTravel(TravelModifyFormDto travelModifyFormDto, Integer travelId, String email) {
        // 본인이 여행일정의 ADMIN(방장)인지 확인
        managerCheck(travelId, email);
        // 나라 변경 (변경 되었을 시에만 동작)
        changeCountry(travelModifyFormDto);
        Travel travel = getTravel(travelId);
        // Travel 변경 감지
        travel.modifyTravel(travelModifyFormDto);
        // 멤버 추가 (추가 되었을 시에만 동작)
        addMember(travelModifyFormDto, travel);
        // Plan Entity 일자 추가/수정 (일자가 변경 되었을 시에만 동작)
        changeDate(travel, travelModifyFormDto);
    }

    private void managerCheck(Integer travelId, String email)  {
        if (!memberRepositoryCustom.isMemberAdmin(travelId, email)) throw new IllegalStateException("권한이 없습니다.");
    }

    private void changeDate(Travel travel, TravelModifyFormDto dto) {

        LocalDate travelStartDate = travel.getStartDate();
        LocalDate dtoStartDate = dto.getStartDate();
        LocalDate travelEndDate = travel.getEndDate();
        LocalDate dtoEndDate = dto.getEndDate();

        if (dtoStartDate != null && !isEqualDate(travelStartDate, dtoStartDate)) {          // 시작일이 변경 되었을 때

            long betweenDateCount;

            if (travelStartDate.isBefore(dtoStartDate)) {           // 시작일이 그 이전으로 변경 되었을 때
                betweenDateCount = PlanDateUtil.getBetweenDateCount(dtoStartDate, travelStartDate, false);
                planRepository.daysPlus(betweenDateCount, travel.getTravelId());
                planService.addPlan(travel, dtoStartDate, travelStartDate.minusDays(1));
            } else {                                                // 시작일이 그 이후로 변경 되었을 때
                // 지우기
                betweenDateCount = PlanDateUtil.getBetweenDateCount(travelStartDate, dtoStartDate, false);
                List<Plan> findPlans = planRepository.findByTravelIdAndDays(travel.getTravelId(), betweenDateCount);
                planRepository.deleteAll(findPlans);
                planRepository.daysMinus(betweenDateCount, travel.getTravelId());

            }
        }
        if (dtoEndDate != null && !isEqualDate(travelEndDate, dtoEndDate)) {        // 종료일만 변경 되었을 때

            long betweenDateCount;

            if (travelEndDate.isBefore(dtoEndDate)) {               // 종료일이 그 이전으로 변경 되었을 때
                // 지우기
                betweenDateCount = PlanDateUtil.getBetweenDateCount(dtoEndDate, travelEndDate, false);
                List<Plan> findPlans = planRepository.findByTravelIdAndDays(travel.getTravelId(), betweenDateCount);
                planRepository.deleteAll(findPlans);
            } else {                                                // 종료일이 그 이후로 변경 되었을 때
                // 추가
                planService.addPlan(travel, travelEndDate.plusDays(1), dtoEndDate);
            }

        }
    }

    private boolean isEqualDate(LocalDate ld1, LocalDate ld2) {
        return ld1.isEqual(ld2);
    }

    private void addMember(TravelModifyFormDto travelModifyFormDto, Travel travel) {
        if (travelModifyFormDto.existMembers()) {
            List<User> findUsers = userRepository.findUserIdsByEmails(travelModifyFormDto.getMembersEmail());
            validationJoinMember(travel.getTravelId(), findUsers);
            findUsers.forEach((user) -> memberRepository.save(new Member(travel, user, JoinStatus.EMPTY, MemberRole.GUEST)));
        }
    }

    private void validationJoinMember(Integer travelId, List<User> users) {
        if (travelRepository.existAlreadyJoin(travelId, users)) throw new IllegalArgumentException("이미 초대를 보냈거나 참여중인 사람이 존재합니다.");
    }

    private Travel getTravel(Integer travelId) {
        return travelRepository.findById(travelId).orElseThrow(() -> new IllegalStateException("잠시 후 이용하세연 !"));
    }

    private void changeCountry(TravelModifyFormDto travelModifyFormDto) {
        if (!StringUtils.hasText(travelModifyFormDto.getCountryIsoAlp2())) return;
        Country country = countryRepository.findByCountryName(travelModifyFormDto.getCountryIsoAlp2())
                .orElseThrow(NoSuchElementException::new);
        travelModifyFormDto.setCountry(country);
    }

    private void makeMembers(TravelFormDto travelFormDto, Travel travel, String email) {
        List<String> membersEmail = travelFormDto.getMembersEmail();
        makeMember(travel,email);
        if(!isEmptyMemberList(membersEmail)){
            makeMembersList(travel, membersEmail);
        }
    }

    private void makeMembersList(Travel travel, List<String> membersEmail) {
        List<User> users = userRepository.findByEmailIn(membersEmail);
        users.forEach(user -> memberRepository.save(new Member(travel, user, JoinStatus.EMPTY, MemberRole.GUEST)));
    }
    @Transactional
    public void makeMember(Travel travel, String email){
        memberRepository.save(new Member(travel, userRepository.findByEmail(email).get(), JoinStatus.YES, MemberRole.ADMIN));
    }

    boolean isEmptyMemberList(List<String> memberList){
        if(memberList == null) return true;
        else return false;
    }

    private String makeInviteUrl(String inviteCode){
        return String.format("%s%s%s", GlobalProperties.FRONT_URL,"?inviteCode=",inviteCode);
    }

}
