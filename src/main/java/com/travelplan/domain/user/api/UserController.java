package com.travelplan.domain.user.api;

import com.travelplan.domain.user.domain.User;
import com.travelplan.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users/{userEmail}")
    public List<String> userEmailFind(@PathVariable String userEmail) {
        return userRepository.findByEmailStartsWith(userEmail)
                .stream().map(User::getEmail)
                .collect(Collectors.toList());
    }

}
