package com.travelplan.global.config.api.dto.currency;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "body")  // 아래 값들의 루트
public class Body {

    @JacksonXmlElementWrapper(localName = "items")  // items란 이름으로 item 데이터들을 묶는다.
    @JacksonXmlProperty(localName = "item")         // 리스트로 출력될 각각의 아이템 요소들의 요소명
    private List<Item> items;


}
