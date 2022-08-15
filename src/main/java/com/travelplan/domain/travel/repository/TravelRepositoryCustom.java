package com.travelplan.domain.travel.repository;

import com.travelplan.domain.travel.dto.TravelModifyDto;
import com.travelplan.domain.travel.dto.TravelModifyFormDto;
import com.travelplan.domain.user.domain.User;

import java.util.List;

public interface TravelRepositoryCustom {

    Boolean existAlreadyJoin(List<User> users);

    TravelModifyDto findModifyDtoByTravelId(Integer travelId);
}
