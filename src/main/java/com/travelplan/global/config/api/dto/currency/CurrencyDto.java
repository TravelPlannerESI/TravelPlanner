package com.travelplan.global.config.api.dto.currency;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 국가별 환율 정보
 * 아래의 XML 구조를 클래스로 표현한 것이다.
 * <response>
 *     <body>
 *         <items>
 *             <item>
 *                 <cntySgn>...</cntySgn>
 *                 <mtryUtNm>...</mtryUtNm>
 *                 ...
 *             </item>
 *             <item>
 *                 <cntySgn>...</cntySgn>
 *                 <mtryUtNm>...</mtryUtNm>
 *                 ...
 *             </item>
 *             <item>
 *                 <cntySgn>...</cntySgn>
 *                 <mtryUtNm>...</mtryUtNm>
 *                 ...
 *             </item>
 *         </items>
 *     </body>
 * </response>
 *
 */
@Getter
@Setter
@JacksonXmlRootElement(localName = "response")   // 아래 값들의 루트
public class CurrencyDto {

    private Body body;

}
