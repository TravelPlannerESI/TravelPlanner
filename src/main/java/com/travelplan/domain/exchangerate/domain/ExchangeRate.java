package com.travelplan.domain.exchangerate.domain;

import com.travelplan.global.config.api.dto.currency.Item;
import com.travelplan.global.entity.base.BaseDateEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRate implements Persistable<String> {

    @Id
    private String cntySgn;    // 국가코드	    AE

    private String mtryUtNm;   // 화폐단위명    	UAE Dirham	화폐단위명

    private String fxrt;       // 환율	        302.7	    환율

    private String currSgn;    // 통화부호	    AED	        통화부호

    private String aplyBgnDt;  // 적용개시일자	20141228	적용개시일자

    private String imexTp;     // 수출입구분	    2	        수출:1, 수입:2

    @CreatedDate
    @Column(name = "created_date",updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;


    public ExchangeRate(Item item) {
        this.cntySgn = item.getCntySgn();
        this.mtryUtNm = item.getMtryUtNm();
        this.fxrt = item.getFxrt();
        this.currSgn = item.getCurrSgn();
        this.aplyBgnDt = item.getAplyBgnDt();
        this.imexTp = item.getImexTp();
    }

    public void update(ExchangeRate exchangeRateInfo) {

        this.cntySgn = exchangeRateInfo.getCntySgn();
        this.mtryUtNm = exchangeRateInfo.getMtryUtNm();
        this.fxrt = exchangeRateInfo.getFxrt();
        this.currSgn = exchangeRateInfo.getCurrSgn();
        this.aplyBgnDt = exchangeRateInfo.getAplyBgnDt();
        this.imexTp = exchangeRateInfo.getImexTp();

    }

    @Override
    public String getId() {
        return this.cntySgn;
    }

    @Override
    public boolean isNew() {
        return this.createdDate == null;
    }
}
