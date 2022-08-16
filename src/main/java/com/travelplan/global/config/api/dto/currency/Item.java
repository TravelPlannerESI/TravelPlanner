package com.travelplan.global.config.api.dto.currency;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private String cntySgn;        // 국가부호	    AE	        한 페이지 결과 수
    private String mtryUtNm;       // 화폐단위명    	UAE Dirham	화폐단위명
    private String fxrt;           // 환율	        302.7	    환율
    private String currSgn;        // 통화부호	    AED	        통화부호
    private String aplyBgnDt;      // 적용개시일자	20141228	적용개시일자
    private String imexTp;         // 수출입구분	    2	        수출:1, 수입:2
}
