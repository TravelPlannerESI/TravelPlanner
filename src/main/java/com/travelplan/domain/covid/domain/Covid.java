package com.travelplan.domain.covid.domain;

import com.travelplan.global.config.api.dto.CountryFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Covid implements Persistable<String> {

    @Id
    private String countryIsoAlp2;    // ISO 2자리 코드

    private String countryNm;          // 한글 국가 명
    private String countryEngNm;      // 영문 국가 명
    private String title;               // 제목
    private String txtOriginCn;       // 글 내용
    private Integer alarmLvl;          // 경보 레벨
    private LocalDate writtenDt;       // 작성일(경보)
    private LocalDate wrtDt;           // 작성일(PCR)

    @CreatedDate
    @Column(name = "created_date",updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @Override
    public String getId() {
        return countryIsoAlp2;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }

    public Covid(CountryFormDto countryFormDto) {
        this.countryIsoAlp2 = countryFormDto.getCountryIsoAlp2();
        this.countryNm = countryFormDto.getCountryNm();
        this.countryEngNm = countryFormDto.getCountryEngNm();
        this.title = countryFormDto.getTitle();
        this.txtOriginCn = countryFormDto.getTxtOriginCn();
        this.alarmLvl = countryFormDto.getAlarmLvl();

        if (countryFormDto.getWrittenDt() != null) {
            this.writtenDt = LocalDate.parse(countryFormDto.getWrittenDt());
        }

        if (countryFormDto.getWrtDt() != null) {
            this.wrtDt = LocalDate.parse(countryFormDto.getWrtDt());
        }

    }
}
