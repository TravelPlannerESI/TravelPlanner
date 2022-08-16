package com.travelplan.domain.travel.dto;

import com.travelplan.domain.country.domain.Country;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TravelModifyFormDto {

    private String travelName;
    private String countryIsoAlp2;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> membersEmail = new ArrayList<>();
    private String inviteCode;
    private Integer totalCost;
    private Country country;

    public boolean existMembers() {
        return membersEmail.size() > 0 ? true : false;
    }
}
