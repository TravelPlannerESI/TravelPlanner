package com.travelplan.domain.travel.dto;

import com.travelplan.global.entity.code.JoinStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TravelJoinResultDto {

    @NotBlank
    private JoinStatus joinStatus;

    @NotBlank
    private String email;

    public TravelJoinResultDto(TravelJoinResultResendDto joinResult) {
        this.joinStatus = joinResult.getJoinStatus();
        this.email = joinResult.getEmail();
    }
    public TravelJoinResultDto(JoinStatus joinStatus, String email) {
        this.joinStatus = joinStatus;
        this.email = email;
    }

}
