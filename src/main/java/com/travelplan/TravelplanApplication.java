package com.travelplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@SpringBootApplication
@EnableJpaAuditing
public class TravelplanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelplanApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(getAuthEmail());
    }

    private String getAuthEmail() {
        DefaultOAuth2User principal = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<String> authEmailOpt = Optional.ofNullable(
                (String) principal.getAttributes().get("email"));

        return authEmailOpt.orElseGet(() -> String.valueOf(((Map<String, Object>) principal.getAttributes().get("kakao_account")).get("email")));
    }

}
