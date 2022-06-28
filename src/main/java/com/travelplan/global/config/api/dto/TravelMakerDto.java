package com.travelplan.global.config.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TravelMakerDto {

    List<TravelMakerContentData> result;

}
