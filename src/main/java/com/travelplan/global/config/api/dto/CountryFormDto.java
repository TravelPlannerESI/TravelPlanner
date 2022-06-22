package com.travelplan.global.config.api.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
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

        // 그룹핑
        Map<String, PcrContentData> groupingPcr = getGroupingByPcr(pcrList);

        // 조합
        List<CountryFormDto> combineResult = combineWarningAndPcr(warningList, groupingPcr);

        return combineResult;
    }

    /**
     * warningList 와 pcrList 의 값을 조합하는 메서드 (ISO 코드 기준)
     * @param warningList
     * @param groupingPcr
     * @return List<CountryFormDto>
     */
    private static List<CountryFormDto> combineWarningAndPcr(List<WarningContentData> warningList, Map<String, PcrContentData> groupingPcr) {
        // 반환 값을 담을 객체 생성
        List<CountryFormDto> combineList = new ArrayList<>();

        for (WarningContentData warningContentData : warningList) {
            PcrContentData pcrContentData = groupingPcr.get(warningContentData.getCountry_iso_alp2());

            // 정보가 있을 시 List 에 add
            if (pcrContentData != null)
                combineList.add(new CountryFormDto(warningContentData, pcrContentData));

        }

        return combineList;
    }

    /**
     * PCR 정보를 Map 으로 Convert Map<ISO 문자, 컨텐츠>
     * @param pcrList
     * @return Map<String, List<PcrContentData>>
     */
    private static Map<String, PcrContentData> getGroupingByPcr(List<PcrContentData> pcrList) {
        // ISO 값에 null 이 존재 하므로 null 값을 제외하고 그룹핑
        return pcrList.stream()
                .distinct()
                .filter(pcr -> pcr.getCountry_iso_alp2() != null)
                .collect(toMap(PcrContentData::getCountry_iso_alp2, o -> o));
    }

}
