package com.travelplan.domain.travel.api;
import com.travelplan.domain.travel.dto.TravelDto;
import com.travelplan.domain.travel.dto.TravelFormDto;
import com.travelplan.domain.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelApi {

    private final TravelService travelService;

    @PostMapping("/api/v1/travel")
    public TravelDto saveTravel(@Validated @RequestBody TravelFormDto travelFormDto) {
        TravelDto travelDto = travelService.addTravel(travelFormDto);
        return travelDto;
    }


}
