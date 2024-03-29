package com.travelplan.domain.travel.dto;

import com.travelplan.domain.travel.domain.Travel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TravelFormDto {

    @NotBlank
    private String travelName;
    @NotNull
    private String countryIsoAlp2;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private List<String> membersEmail;
    private String inviteCode;
    private Integer totalCost;


    public boolean existMembers() {
        return membersEmail.size() > 0 ? true : false;
    }

}
