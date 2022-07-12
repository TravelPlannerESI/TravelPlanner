package com.travelplan.global.config;

import com.travelplan.global.config.auth.oauth2.config.properties.GlobalProperties;
import com.travelplan.global.config.webconfig.OauthUserArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(GlobalProperties.FRONT_URL)
                .allowedHeaders(GlobalProperties.ALLOWED_HEADERS)
                .allowedMethods(GlobalProperties.ALLOWED_METHODS.split(","))
                .maxAge(GlobalProperties.MAX_AGE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new OauthUserArgumentResolver());
    }
}
