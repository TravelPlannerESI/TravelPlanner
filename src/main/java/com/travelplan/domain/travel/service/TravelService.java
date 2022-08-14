package com.travelplan.domain.travel.service;

import com.travelplan.domain.country.domain.Country;
import com.travelplan.domain.country.repository.CountryRepository;
import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.repository.MemberRepository;
import com.travelplan.domain.plan.service.PlanService;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void makeMember(Travel travel, String email){
        memberRepository.save(new Member(travel, userRepository.findByEmail(email).get(), JoinStatus.YES, MemberRole.ADMIN));
    }

    boolean isEmptyMemberList(List<String> memberList){
        if(memberList == null) return true;
        else return false;
    }

}
