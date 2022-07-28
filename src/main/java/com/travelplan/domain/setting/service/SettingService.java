package com.travelplan.domain.setting.service;

import com.travelplan.domain.member.domain.Member;
import com.travelplan.domain.member.repository.MemberRepository;
import com.travelplan.domain.member.web.repository.MemberRepositoryCustom;
import com.travelplan.domain.setting.web.repository.SettingRepositoryCustom;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelCountryDto;
import com.travelplan.domain.setting.web.repository.dto.SettingTravelMembers;
import com.travelplan.domain.travel.domain.Travel;
import com.travelplan.domain.travel.dto.TravelJoinResultDto;
import com.travelplan.domain.travel.repository.TravelRepository;
import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.entity.code.JoinStatus;
import com.travelplan.global.entity.code.MemberRole;
import com.travelplan.global.exception.customexception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SettingService {

    private final SettingRepositoryCustom settingRepositoryCustom;
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final MemberRepositoryCustom memberRepositoryCustom;

    @Transactional(readOnly = true)
    public Page<SettingTravelCountryDto> findTravelsByLoginUser(String loginUserEmail, Pageable pageable) {
        return settingRepositoryCustom.selectTravelsByLoginUser(loginUserEmail, pageable);
    }

    @Transactional(readOnly = true)
    public List<SettingTravelMembers> findTravelMembers(int travelId) {
        return settingRepositoryCustom.selectMembers(travelId);
    }

    public void addMembers(int travelId, List<String> newEmails) {

        Travel travel = travelRepository.findById(travelId)
                                            .orElseThrow(() -> new IdNotFoundException("해당하는 여행 정보가 없습니다."));

        List<User> users = userRepository.findByEmailIn(newEmails);

        users.forEach((user) -> {
            memberRepository.save(new Member(travel, user, JoinStatus.EMPTY, MemberRole.GUEST));
        });
    }



    @Transactional
    public void updateJoinStatus(String inviteCode, TravelJoinResultDto joinResult) {

        // travel_id 조회
        Travel travel = travelRepository.findByInviteCode(inviteCode);

        // member_id 조회
        Integer memberId = memberRepositoryCustom.findMemberId(travel, joinResult.getEmail());
        Member findMember = memberRepository.findById(memberId)
                                                .orElseThrow(() -> new IdNotFoundException("해당 사용자가 '" + travel.getTravelName() + "' 여행일정에 존재하지 않습니다."));

        findMember.setJoinStatus(joinResult.getJoinStatus());
    }



    // 내가 만들지 않고 초대된 여행

    @Transactional(readOnly = true)
    public Page<SettingTravelCountryDto> findInvitedTravels(String loginUserEmail, Pageable pageable) {
        return settingRepositoryCustom.selectInvitedTravel(loginUserEmail, pageable);
    }

}
