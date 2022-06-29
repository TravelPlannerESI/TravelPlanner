package com.travelplan.global.config.api;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.travelplan.global.config.api.dto.*;
import java.util.List;

class RestTemplateApiTest {

    @Test
    void warning_prc_조합_결과() {

        List<CountryFormDto> result = CountryFormDto.of(getWarningList(), getPcrList());

        for (CountryFormDto countryFormDto : result) {
            System.out.println("countryFormDto = " + countryFormDto);
        }
    }

    private WarningDto getWarningList() {

        WarningDto warningDto = new WarningDto();

        List<WarningContentData> warningContentData = Arrays.asList(
            new WarningContentData(2,"Ghana", "GH", "가나", "2022-04-28"),
            new WarningContentData(1,"Gabon", "GA", "가봉", "2022-04-28"),
            new WarningContentData(2,"Guyana", "GY", "가이아나", "2022-04-14"),
            new WarningContentData(2,"Gambia", "GM", "감비아", "2022-04-28"),
            new WarningContentData(2,"Guatemala", "GT", "과테말라", "2022-04-14"),
            new WarningContentData(null,"Vatican", "VA", "교황청", "2020-03-16"),
            new WarningContentData(2,"Grenada", "GD", "그레나다", "2022-04-14"),
            new WarningContentData(2,"Greece", "GR", "그리스", "2022-04-14"),
            new WarningContentData(2,"Guinea", "GN", "기니", "2022-04-28"),
            new WarningContentData(2,"Namibia", "NA", "나미비아", "2022-04-28"),
            new WarningContentData(null,"Nauru", "NR", "나우루", "2020-07-17"),
            new WarningContentData(3,"Nigeria", "NG", "나이지리아", "2022-04-28"),
            new WarningContentData(1,"Netherlands", "NL", "네덜란드", "2022-04-1"),
            new WarningContentData(1,"Nepal", "NP", "네팔연방", "2022-04-14"),
            new WarningContentData(1,"Norway", "NO", "노르웨이", "2022-04-14"),
            new WarningContentData(null,"Niue", "NU", "니우에", "2020-07-17"),
            new WarningContentData(3,"Niger", "NE", "니제르", "2022-04-28"),
            new WarningContentData(2,"Nicaragua", "NI", "니카라과", "2022-04-14"),
            new WarningContentData(null,"Taiwan", "TW", "대만", "2020-07-17"),
            new WarningContentData(1,"Denmark", "DK", "덴마크", "2022-04-14"),
            new WarningContentData(2,"Dominica", "DM", "도미니카연방", "2022-04-14"),
            new WarningContentData(2,"Germany", "DE", "독일", "2022-04-14"),
            new WarningContentData(2,"Laos", "LA", "라오스", "2022-04-14"),
            new WarningContentData(2,"Liberia", "LR", "라이베리아", "2022-04-28"),
            new WarningContentData(2,"Latvia", "LV", "라트비아", "2022-04-14"),
            new WarningContentData(4,"Russia", "RU", "러시아", "2022-03-08"),
            new WarningContentData(3,"Lebanon", "LB", "레바논", "2022-04-14"),
            new WarningContentData(null,"Lesotho", "LS", "레소토", "2020-07-17"),
            new WarningContentData(2,"Romania", "RO", "루마니아", "2022-04-14"),
            new WarningContentData(2,"Luxembourg", "LU", "룩셈부르크", "2022-04-1"),
            new WarningContentData(2,"Rwanda", "RW", "르완다", "2022-04-28"),
            new WarningContentData(4,"Libya", "LY", "리비아", "2022-04-29"),
            new WarningContentData(2,"Lithuania", "LT", "리투아니아", "2022-04-14"),
            new WarningContentData(2,"Liechtenstein", "LI", "리히텐슈타인", "2022-"),
            new WarningContentData(2,"Madagascar", "MG", "마다가스카르", "2022-04-"),
            new WarningContentData(null,"Malawi", "MW", "말라위", "2020-07-17"),
            new WarningContentData(3,"Malaysia", "MY", "말레이시아", "2022-04-14"),
            new WarningContentData(3,"Mali", "ML", "말리", "2022-04-29"),
            new WarningContentData(2,"Mexico", "MX", "멕시코", "2022-04-14"),
            new WarningContentData(2,"Monaco", "MC", "모나코", "2022-04-14"),
            new WarningContentData(3,"Morocco", "MA", "모로코", "2022-04-28"),
            new WarningContentData(2,"Mauritius", "MU", "모리셔스", "2022-04-28")
        );

        warningDto.setData(warningContentData);
        return warningDto;
    }

    private PcrDto getPcrList() {

        List<PcrContentData> pcrContentData = Arrays.asList(
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("UA", "우크라이나", "Ukraine","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("DK", "덴마크", "Denmark","","",""),
                new PcrContentData("HK", "홍콩", "Hongkong","","",""),
                new PcrContentData("NP", "네팔연방", "Nepal","","",""),
                new PcrContentData("TR", "터키", "Turkey","","",""),
                new PcrContentData("ZA", "남아프리카공화국", "South Africa","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("FR", "프랑스", "France","","",""),
                new PcrContentData("PT", "포르투갈", "Portugal","","",""),
                new PcrContentData("PH", "필리핀", "Philippines","","",""),
                new PcrContentData("FR", "프랑스", "France","","",""),
                new PcrContentData("NL", "네덜란드", "Netherlands","","",""),
                new PcrContentData("DE", "독일", "Germany","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("DK", "덴마크", "Denmark","","",""),
                new PcrContentData("LB", "레바논", "Lebanon","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("JP", "일본", "Japan","","",""),
                new PcrContentData("TR", "터키", "Turkey","","",""),
                new PcrContentData("NL", "네덜란드", "Netherlands","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("IN", "인도", "India","","",""),
                new PcrContentData("SE", "스웨덴", "Sweden","","",""),
                new PcrContentData("PT", "포르투갈", "Portugal","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("RS", "세르비아", "Serbia","","",""),
                new PcrContentData("LB", "레바논", "Lebanon","","",""),
                new PcrContentData("UZ", "우즈베키스탄", "Uzbekistan","","",""),
                new PcrContentData("IN", "인도", "India","","",""),
                new PcrContentData("CL", "칠레", "Chile","","",""),
                new PcrContentData("PT", "포르투갈", "Portugal","","",""),
                new PcrContentData("AE", "아랍에미리트", "United Arab Emirates : UAE","","",""),
                new PcrContentData("DK", "덴마크", "Denmark","","",""),
                new PcrContentData("PL", "폴란드", "Poland","","",""),
                new PcrContentData("RO", "루마니아", "Romania","","",""),
                new PcrContentData("IT", "이탈리아", "Italia","","",""),
                new PcrContentData("FR", "프랑스", "France","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("IN", "인도", "India","","",""),
                new PcrContentData("KZ", "카자흐스탄", "Kazakhstan","","",""),
                new PcrContentData("LB", "레바논", "Lebanon","","",""),
                new PcrContentData("AE", "아랍에미리트", "United Arab Emirates : UAE","","",""),
                new PcrContentData("PT", "포르투갈", "Portugal","","",""),
                new PcrContentData("PL", "폴란드", "Poland","","",""),
                new PcrContentData("EG", "이집트", "Egypt","","",""),
                new PcrContentData("YE", "예멘", "Yemen","","",""),
                new PcrContentData("GT", "과테말라", "Guatemala","","",""),
                new PcrContentData("DE", "독일", "Germany","","",""),
                new PcrContentData("LB", "레바논", "Lebanon","","",""),
                new PcrContentData("MX", "멕시코", "Mexico","","",""),
                new PcrContentData("US", "미합중국", "United States of America","","",""),
                new PcrContentData("UG", "우간다", "Uganda","","",""),
                new PcrContentData("US", "미합중국", "United States of America","","",""),
                new PcrContentData("HU", "헝가리", "Hungary","","",""),
                new PcrContentData("HU", "헝가리", "Hungary","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("LA", "라오스", "Laos","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("BO", "볼리비아", "Bolivia","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("RU", "러시아", "Russia","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("SZ", "에스와티니", "Eswatini","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("MX", "멕시코", "Mexico","","",""),
                new PcrContentData("CH", "스위스", "Swiss","","",""),
                new PcrContentData("PH", "필리핀", "Philippines","","",""),
                new PcrContentData("US", "미합중국", "United States of America","","",""),
                new PcrContentData("PH", "필리핀", "Philippines","","",""),
                new PcrContentData("AR", "아르헨티나", "Argentina","","",""),
                new PcrContentData("PE", "페루", "Peru","","",""),
                new PcrContentData("BO", "볼리비아", "Bolivia","","",""),
                new PcrContentData("EC", "에콰도르", "Ecuador","","",""),
                new PcrContentData("US", "미합중국", "United States of America","","",""),
                new PcrContentData("AR", "아르헨티나", "Argentina","","",""),
                new PcrContentData("TH", "태국", "Thailand","","",""),
                new PcrContentData("SG", "싱가포르", "Singapore","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("MY", "말레이시아", "Malaysia","","",""),
                new PcrContentData("MG", "마다가스카르", "Madagascar","","",""),
                new PcrContentData("VU", "바누아투", "Vanuatu","","",""),
                new PcrContentData("MH", "마셜제도", "Marshall Islands","","",""),
                new PcrContentData("RU", "러시아", "Russia","","",""),
                new PcrContentData("RU", "러시아", "Russia","","",""),
                new PcrContentData("SG", "싱가포르", "Singapore","","",""),
                new PcrContentData("VN", "베트남", "Vietnam","","",""),
                new PcrContentData("KH", "캄보디아", "Cambodia","","",""),
                new PcrContentData("JP", "일본", "Japan","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("IR", "이란", "Iran","","",""),
                new PcrContentData("DE", "독일", "Germany","","",""),
                new PcrContentData("IE", "아일랜드", "Ireland","","",""),
                new PcrContentData("AE", "아랍에미리트", "United Arab Emirates : UAE","","",""),
                new PcrContentData("IT", "이탈리아", "Italia","","",""),
                new PcrContentData("AR", "아르헨티나", "Argentina","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("IT", "이탈리아", "Italia","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("TM", "투르크메니스탄", "Turkmenistan","","",""),
                new PcrContentData("KZ", "카자흐스탄", "Kazakhstan","","",""),
                new PcrContentData("ET", "에티오피아", "ETC","","",""),
                new PcrContentData("KG", "키르기스스탄", "Kyrgyz","","",""),
                new PcrContentData("KI", "키리바시", "Kiribati","","",""),
                new PcrContentData("KZ", "카자흐스탄", "Kazakhstan","","",""),
                new PcrContentData("GR", "그리스", "Greece","","",""),
                new PcrContentData("TM", "투르크메니스탄", "Turkmenistan","","",""),
                new PcrContentData("VN", "베트남", "Vietnam","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("SG", "싱가포르", "Singapore","","",""),
                new PcrContentData("GB", "영국", "United Kingdom","","",""),
                new PcrContentData("HK", "홍콩", "Hongkong","","",""),
                new PcrContentData("KW", "쿠웨이트", "Kuwait","","",""),
                new PcrContentData("PG", "파푸아뉴기니", "Papua New Guinea : PNG","","",""),
                new PcrContentData("null", "ALL", "null","","",""),
                new PcrContentData("DE", "독일", "Germany","","",""),
                new PcrContentData("KH", "캄보디아", "Cambodia","","",""),
                new PcrContentData("AE", "아랍에미리트", "United Arab Emirates : UAE","","",""),
                new PcrContentData("KZ", "카자흐스탄", "Kazakhstan","","",""),
                new PcrContentData("MM", "미얀마", "Myanmar","","",""),
                new PcrContentData("CN", "중국", "China","","",""),
                new PcrContentData("VN", "베트남", "Vietnam","","",""),
                new PcrContentData("SG", "싱가포르", "Singapore","","",""),
                new PcrContentData("null", "ALL", "null","","","")
        );

        PcrDto pcrDto = new PcrDto();
        pcrDto.setData(pcrContentData);
        return pcrDto;
    }

}