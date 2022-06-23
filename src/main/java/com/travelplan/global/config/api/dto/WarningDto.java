package com.travelplan.global.config.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class WarningDto implements Serializable {

    List<WarningContentData> data;

}
