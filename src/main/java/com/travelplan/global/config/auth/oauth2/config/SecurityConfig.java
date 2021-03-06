package com.travelplan.global.config.auth.oauth2.config;

import com.travelplan.global.config.auth.oauth2.exception.CustomAuthenticationEntryPoint;
import com.travelplan.global.config.auth.oauth2.handler.CustomAccessDeniedHandler;
import com.travelplan.global.config.auth.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.travelplan.global.config.auth.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.travelplan.global.config.auth.oauth2.service.CustomOAuth2UserService;
import com.travelplan.global.entity.code.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security 설정들을 활성화 시켜준다.
// WebSecurityConfigurerAdapter 2.7버전부터 막힘.
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private static final String INITIAL_COUNTRY_COVID_INFO = "/api/v1/country";
    private static final String INVITED_MEMBER = "/api/v1/travel/*/*";

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //URL별 권한 관리를 설정하는 옵션의 시작점이다.
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**", "/login**", INITIAL_COUNTRY_COVID_INFO, INVITED_MEMBER).permitAll()
                .antMatchers("/api/v1/**").hasRole(UserRole.USER.name())
                .antMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                //위에서 설정된값 이외에 나머지 요청들 설정 현재 설정은 인증된 사용자만 가능하게 변경했다.
                .anyRequest().authenticated()
                .and()
                .logout().deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/").permitAll()
                .and()
                //권한이 없다면 ? -> 401내려준다.
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        return http.build();
    }

}
