package com.travelplan.domain.travel.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.repository.MemberRepository;
import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.domain.plan.util.PlanDateUtil;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.dto.TravelModifyFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        travelDto.setInviteCode(inviteCode);

        // 날짜 별 plan 추가
        planService.addPlan(travel, travel.getStartDate(), travel.getEndDate());

        return travelDto;
    }

    @Transactional
    public void modifyTravel(TravelModifyFormDto travelModifyFormDto, Integer travelId) {
        changeCountry(travelModifyFormDto);
        Travel travel = getTravel(travelId);
        travel.modifyTravel(travelModifyFormDto);
        addMember(travelModifyFormDto, travel);
    }

    private void changeDate(Travel travel, TravelModifyFormDto dto) {
        LocalDate travelStartDate = travel.getStartDate();
        LocalDate dtoStartDate = dto.getStartDate();
        LocalDate travelEndDate = travel.getEndDate();
        LocalDate dtoEndDate = dto.getEndDate();

        if (!isEqualDate(travelStartDate, dtoStartDate) &&
            !isEqualDate(travelEndDate, dtoEndDate)) {                // 시작일과 종료일 두가지 다 변경 되었을 때

            if (travelStartDate.isBefore(dtoStartDate)) {           // 시작일이 그 이전으로 변경 되었을 때
                // 시작일이 이전으로 변경되면 몇일자가 추가되는지 계산해야되고 그 갯수만큼 기존 데이터 days 더하기 해줘야 함
                long betweenDateCount = PlanDateUtil.getBetweenDateCount(travelStartDate, dtoStartDate, false);

            } else {                                                // 시작일이 그 이후로 변경 되었을 때
                // 시작일이 이후로 변경되면 몇일자가 추가되는지 계산해야되고 그 갯수만큼 기존 데이터 days 빼기 해줘야 함, 없어진 일자에 대해 제거 필요
            }

            if (travelEndDate.isBefore(dtoEndDate)) {               // 종료일이 그 이전으로 변경 되었을 때
                // 종료일이 이전으로 변경되면 없어진 일자에 대해 제거 필요
            } else {                                                // 종료일이 그 이후로 변경 되었을 때
                // 종료일이 이후로 변경되면 추가
            }

        } else if (!isEqualDate(travelStartDate, dtoStartDate)) {    // 시작일만 변경 되었을 때

            if (travelStartDate.isBefore(dtoStartDate)) {           // 시작일이 그 이전으로 변경 되었을 때

            } else {                                                // 시작일이 그 이후로 변경 되었을 때

            }

        } else if (!!isEqualDate(travelEndDate, dtoEndDate)) {        // 종료일만 변경 되었을 때

            if (travelEndDate.isBefore(dtoEndDate)) {               // 종료일이 그 이전으로 변경 되었을 때

            } else {                                                // 종료일이 그 이후로 변경 되었을 때

            }

        }
    }

    private boolean isEqualDate(LocalDate ld1, LocalDate ld2) {
        return ld1.isEqual(ld2);
    }

    private void addMember(TravelModifyFormDto travelModifyFormDto, Travel travel) {
        if (travelModifyFormDto.existMembers()) {
            List<User> findUsers = userRepository.findUserIdsByEmails(travelModifyFormDto.getMembersEmail());
            validationJoinMember(findUsers);
            findUsers.forEach((user) -> memberRepository.save(new Member(travel, user, JoinStatus.EMPTY, MemberRole.GUEST)));
        }
    }

    private void validationJoinMember(List<User> users) {
        if (travelRepository.existAlreadyJoin(users) != null) throw new IllegalArgumentException("이미 초대를 보냈거나 참여중인 사람이 존재합니다.");
    }

    private Travel getTravel(Integer travelId) {
        return travelRepository.findById(travelId).orElseThrow(() -> new IllegalStateException("잠시 후 이용하세연 !"));
    }

    private void changeCountry(TravelModifyFormDto travelModifyFormDto) {
        Country country = countryRepository.findByCountryName(travelModifyFormDto.getCountryIsoAlp2())
                .orElseThrow(NoSuchElementException::new);
        travelModifyFormDto.setCountry(country);
    }

    private void makeMembers(TravelFormDto travelFormDto, Travel travel, String email) {
        List<String> membersEmail = travelFormDto.getMembersEmail();
        membersEmail.add(email);
        List<User> users = userRepository.findByEmailIn(membersEmail);
        users.forEach(user -> {
            if (email.equals(user.getEmail()))
                memberRepository.save(new Member(travel, user, JoinStatus.YES, MemberRole.ADMIN));
            else memberRepository.save(new Member(travel, user, JoinStatus.EMPTY, MemberRole.GUEST));
        });
        membersEmail.remove(membersEmail.size()-1);
    }



}
