package com.travelplan.global.config.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CoordinateDto {

    List<CoordinateContentData> result;

}
