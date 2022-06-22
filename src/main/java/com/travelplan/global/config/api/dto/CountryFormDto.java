package com.travelplan.global.config.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class CountryFormDto {
    private String country_iso_alp2;    // ISO 2자리 코드
    private String country_nm;          // 한글 국가 명
    private String country_eng_nm;      // 영문 국가 명
    private String title;               // 제목
    private String txt_origin_cn;       // 글 내용
    private Integer alarm_lvl;          // 경보 레벨
    private String written_dt;          // 작성일(경보)
    private String wrt_dt;              // 작성일(PCR)

    private CountryFormDto(WarningContentData warningDto, PcrContentData pcrDto) {
        country_iso_alp2 = warningDto.getCountry_iso_alp2();
        country_nm = warningDto.getCountry_nm();
        country_eng_nm = warningDto.getCountry_eng_nm();
        title = pcrDto.getTitle();
        txt_origin_cn = pcrDto.getTxt_origin_cn();
        alarm_lvl = warningDto.getAlarm_lvl();
        written_dt = warningDto.getWritten_dt();
        wrt_dt = pcrDto.getWrt_dt();
    }

    public static List<CountryFormDto> of(WarningDto warningDto, PcrDto pcrDto) {

        List<WarningContentData> warningList = warningDto.getData();
        List<PcrContentData> pcrList = pcrDto.getData();

        // PCR 정보를 Map 으로 Convert Map<ISO 문자, 컨텐츠>
        Map<String, List<PcrContentData>> groupingPcr = pcrList.stream()
                .filter(pcr -> pcr.getCountry_iso_alp2() != null)
                .collect(Collectors.groupingBy(PcrContentData::getCountry_iso_alp2));

        List<CountryFormDto> countryList = new ArrayList<>();

        // 격리 정보를 루프로 돌려 ISO 문자로 groupingPcr(Map) 의 키값에 대입하여 정보를 얻어온다.
        // 정보가 있을 시 List 에 add
        for (WarningContentData warningContentData : warningList) {
            List<PcrContentData> pcrContentData = groupingPcr.get(warningContentData.getCountry_iso_alp2());

            if(pcrContentData != null) {
                PcrContentData pcrContentData1 = pcrContentData.get(0);

                countryList.add(new CountryFormDto(warningContentData, pcrContentData1));
            }
        }

        return countryList;
    }

}
