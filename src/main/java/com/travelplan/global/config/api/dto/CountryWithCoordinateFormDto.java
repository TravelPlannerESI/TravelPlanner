package com.travelplan.global.config.api.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
@ToString
public class CountryWithCoordinateFormDto {
    private String country_iso_alp2;    // ISO 2자리 코드
    private String country_nm;          // 한글 국가 명
    private String country_eng_nm;      // 영문 국가 명
    private String title;               // 제목
    private String txt_origin_cn;       // 글 내용
    private Integer alarm_lvl;          // 경보 레벨
    private String written_dt;          // 작성일(경보)
    private String wrt_dt;              // 작성일(PCR)
    private String lat;                 // 위도
    private String lng;                 // 경도


    private CountryWithCoordinateFormDto(WarningContentData warningDto) {
        country_iso_alp2 = warningDto.getCountry_iso_alp2();
        country_nm = warningDto.getCountry_nm();
        country_eng_nm = warningDto.getCountry_eng_nm();
        alarm_lvl = warningDto.getAlarm_lvl();
        written_dt = warningDto.getWritten_dt();
    }

    private CountryWithCoordinateFormDto(WarningContentData warningDto, PcrContentData pcrDto) {
        country_iso_alp2 = warningDto.getCountry_iso_alp2();
        country_nm = warningDto.getCountry_nm();
        country_eng_nm = warningDto.getCountry_eng_nm();
        title = pcrDto.getTitle();
        txt_origin_cn = pcrDto.getTxt_origin_cn();
        alarm_lvl = warningDto.getAlarm_lvl();
        written_dt = warningDto.getWritten_dt();
        wrt_dt = pcrDto.getWrt_dt();
    }

    private CountryWithCoordinateFormDto(WarningContentData warningDto, CoordinateContentData tmDto) {
        country_iso_alp2 = warningDto.getCountry_iso_alp2();
        country_nm = warningDto.getCountry_nm();
        country_eng_nm = warningDto.getCountry_eng_nm();
        alarm_lvl = warningDto.getAlarm_lvl();
        written_dt = warningDto.getWritten_dt();
        lat = tmDto.getLat();
        lng = tmDto.getLng();
    }

    private CountryWithCoordinateFormDto(WarningContentData warningDto, PcrContentData pcrDto, CoordinateContentData tmDto) {
        country_iso_alp2 = warningDto.getCountry_iso_alp2();
        country_nm = warningDto.getCountry_nm();
        country_eng_nm = warningDto.getCountry_eng_nm();
        title = pcrDto.getTitle();
        txt_origin_cn = pcrDto.getTxt_origin_cn();
        alarm_lvl = warningDto.getAlarm_lvl();
        written_dt = warningDto.getWritten_dt();
        wrt_dt = pcrDto.getWrt_dt();
        lat = tmDto.getLat();
        lng = tmDto.getLng();
    }


    public static List<CountryWithCoordinateFormDto> of(WarningDto warningDto, PcrDto pcrDto, CoordinateDto tmDto) {
        List<WarningContentData> warningList = warningDto.getData();
        List<PcrContentData> pcrList = pcrDto.getData();
        List<CoordinateContentData> coordinateContentList = tmDto.getResult();


        // 그룹핑
        Map<String, PcrContentData> groupingPcr = getGroupingByPcr(pcrList);
        Map<String, CoordinateContentData> groupingByCdData = getGroupingByCoordinate(coordinateContentList);


        // 조합
        return combineWarningAndPcrAndCoordination(warningList, groupingPcr, groupingByCdData);
    }


    /**
     * warningList, pcrList, coordinateList 의 값을 조합하는 메서드 (ISO 코드 기준)
     * @param warningList -
     * @param groupingPcr -
     * @param groupingCoordinate -
     * @return List<CountryWtihCoordinateFormDto>
     */
    private static List<CountryWithCoordinateFormDto> combineWarningAndPcrAndCoordination(
                                                            List<WarningContentData> warningList,
                                                            Map<String, PcrContentData> groupingPcr,
                                                            Map<String, CoordinateContentData> groupingCoordinate) {
        // 반환 값을 담을 객체 생성
        List<CountryWithCoordinateFormDto> combineList = new ArrayList<>();


        for (WarningContentData warningContentData : warningList) {

            PcrContentData pcrContentData = groupingPcr.get(warningContentData.getCountry_iso_alp2());
            CoordinateContentData coordinateData = groupingCoordinate.get(warningContentData.getCountry_iso_alp2());

            // warning 정보가 기준이 된다 (data 의 수가 더 많기 때문)
            if(pcrContentData == null && coordinateData == null) {
                combineList.add(new CountryWithCoordinateFormDto(warningContentData));
            } else if (pcrContentData == null && coordinateData != null) {
                combineList.add(new CountryWithCoordinateFormDto(warningContentData, coordinateData));
            } else if (pcrContentData != null && coordinateData == null) {
                combineList.add(new CountryWithCoordinateFormDto(warningContentData, pcrContentData));
            } else {
                combineList.add(new CountryWithCoordinateFormDto(warningContentData, pcrContentData, coordinateData));
            }
        }
        return combineList;
    }


    /**
     * PCR 정보를 Map 으로 Convert Map<ISO 문자, 컨텐츠>
     * @param pcrList -
     * @return Map<String, PcrContentData>
     */
    private static Map<String, PcrContentData> getGroupingByPcr(List<PcrContentData> pcrList) {
        // ISO 값에 null 이 존재 하므로 null 값을 제외하고 그룹핑
        return pcrList.stream()
                .distinct()     // ISO 기준으로 동일하다면 제거
                .filter(pcr -> pcr.getCountry_iso_alp2() != null)
                .collect(toMap(PcrContentData::getCountry_iso_alp2, o -> o));
    }


    /**
     * 좌표 정보를 Map 으로 Convert Map<국가 코드, 컨텐츠>
     * @param coordinateList -
     * @return Map<String, CoordinateContentData>
     */
    private static Map<String, CoordinateContentData> getGroupingByCoordinate(List<CoordinateContentData> coordinateList) {
        // ISO 값에 null 이 존재 하므로 null 값을 제외하고 그룹핑
        return coordinateList.stream()
                .distinct()     // ISO 기준으로 동일하다면 제거
                .filter(cdData -> cdData.getCountry_code() != null)
                .collect(toMap(CoordinateContentData::getCountry_code, o -> o));
    }

}
