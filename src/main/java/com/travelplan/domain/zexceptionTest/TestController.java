package com.travelplan.domain.zexceptionTest;

import com.travelplan.global.exception.TempException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
public class TestController {


    @GetMapping("/api/v1/tmp")
    public String tmp(@RequestParam("aaa") String aaa) {

        if (aaa.equals("ex")) {
            throw new TempException("에러 발생!");
        }

        return "hello";
    }

    @PostMapping("/api/v1/add")
    public Object add(@RequestBody @Valid TestDTO dto) {

        /*
            {
                "id": "hello",
                "name": "",
                "email": "",
                "phone": "1"
            }
         */

        System.out.println("dto = " + dto);

        return "hello";
    }

}
