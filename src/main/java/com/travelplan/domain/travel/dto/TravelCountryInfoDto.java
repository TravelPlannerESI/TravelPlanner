package com.travelplan.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TravelCountryInfoDto {


    private Integer travelId;
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String inviteCode;
    private Integer totalCost;

    private String countryName;       // 국가명
    private String countryIsoAlp2;    // ISO 2자리 코드
    private String fxrt;              // 환율       302.7       환율
    private String currSgn;           // 통화부호    AED         통화부호
    private String mtryUtNm;          // 화폐단위명  UAE Dirham	화폐단위명

//    private List<TravelTheme> travelTheme = new ArrayList<>();

    private List<ChartDataDto> chartData = new ArrayList<>();

    @QueryProjection
    public TravelCountryInfoDto(Integer travelId, String travelName, LocalDate startDate, LocalDate endDate,
                                String inviteCode, Integer totalCost, String countryName, String countryIsoAlp2,
                                String fxrt, String currSgn, String mtryUtNm) {
        this.travelId = travelId;
        this.travelName = travelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inviteCode = inviteCode;
        this.totalCost = totalCost;

        this.countryName = countryName;
        this.countryIsoAlp2 = countryIsoAlp2;

        this.fxrt = fxrt;
        this.currSgn = currSgn;
        this.mtryUtNm = mtryUtNm;
    }


}
