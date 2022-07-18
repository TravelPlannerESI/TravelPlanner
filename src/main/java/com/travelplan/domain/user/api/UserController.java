package com.travelplan.domain.user.api;

import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import com.travelplan.global.config.webconfig.annotation.OauthUser;
import com.travelplan.global.utils.responsedto.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static  com.travelplan.global.utils.responsedto.constant.ResponseConstant.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users/{userEmail}")
    public ResponseEntity<ResponseData> userEmailFind(@PathVariable String userEmail , @OauthUser SessionUser user) {
        String myEmail = user.getEmail();
        List<String> emailList = userRepository.findByEmailStartsWith(userEmail)
                .stream().map(User::getEmail)
                .filter(email -> !email.equals(myEmail))
                .collect(Collectors.toList());
        ResponseData<List<String>> resData = new ResponseData<>(emailList, SEARCH.getSuccessCode(), SEARCH.getSuccessMessage());
        return ResponseEntity.ok(resData);
    }

}
