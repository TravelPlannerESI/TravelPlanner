package com.travelplan.domain.travel.dto;

import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TravelJoinResultResponseDto {

    @NotBlank
    private JoinStatus joinStatus;

}
