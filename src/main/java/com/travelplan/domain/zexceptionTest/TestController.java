package com.travelplan.domain.zexceptionTest;

import com.travelplan.global.exception.TempException;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.ADD;
import static com.travelplan.global.utils.responsedto.constant.ResponseConstant.SEARCH;


@Slf4j
@RestController
public class TestController {


    @GetMapping("/api/v1/tmp")
    public ResponseEntity<ResponseData<String>> tmp(@RequestParam("aaa") String aaa) {

        if (aaa.equals("ex")) {
            throw new TempException("에러 발생!");
        }

        ResponseData<String> response = new ResponseData<>(aaa, ADD.getSuccessCode(), ADD.getSuccessMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/api/v1/add")
    public ResponseEntity<ResponseData<TestDTO>> add(@RequestBody @Valid TestDTO dto) {

        /*
            테스트 JSON 데이터
            {
                "id": "hello",
                "name": "",
                "email": "",
                "phone": "1"
            }
         */

        System.out.println("dto = " + dto);

        ResponseData<TestDTO> response = new ResponseData<>(dto, ADD.getSuccessCode(), ADD.getSuccessMessage());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
