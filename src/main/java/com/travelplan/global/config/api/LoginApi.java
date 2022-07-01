package com.travelplan.global.config.api;

import com.travelplan.global.config.auth.oauth2.session.SessionUser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class LoginApi {

    @PostMapping("/oauth/success")
    public SessionUser successOauth(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        SessionUser user = (SessionUser) session.getAttribute("user");
        log.info("USER = {} ",user);
        return user;
    }

}
