package com.travelplan.domain.travel.dto;

import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TravelJoinResultResendDto {

    @NotBlank
    private JoinStatus joinStatus;

    @NotBlank
    private String email;

}
