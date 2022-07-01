package com.travelplan.global.config.auth.oauth2.exception;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.util.EncodingUtils;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 메시지 수정 필요
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writeValueAsString(new Unauthorized());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(s);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println(s);
    }

    @Getter
    static class Unauthorized{
        private String msg = "접근 권한이 없습니다.";
    }


}
