package com.travelplan.domain.travel.dto;

import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelJoinResultDto {

    private JoinStatus joinStatus;

    private String email;

}
