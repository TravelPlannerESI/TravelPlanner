package com.travelplan.domain.user.api;

import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import com.travelplan.global.config.auth.oauth2.attribute.OAuthAttributes;
import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users/{userEmail}")
    public List<String> userEmailFind(@PathVariable String userEmail , HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SessionUser user = (SessionUser) session.getAttribute("user");
        String myEmail = user.getEmail();

        return userRepository.findByEmailStartsWith(userEmail)
                .stream().map(User::getEmail)
                .filter(email -> !email.equals(myEmail))
                .collect(Collectors.toList());
    }

}
