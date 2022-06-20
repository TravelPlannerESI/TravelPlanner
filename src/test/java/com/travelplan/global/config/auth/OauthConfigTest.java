package com.travelplan.global.config.auth;

import io.restassured.RestAssured;
import org.aspectj.lang.annotation.Around;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OauthConfigTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8095;
    }


    @Test
    public void google_로그인_시_302와_함께_로그인_페이지가_보인다 () throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorization/google")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://accounts.google.com/o/oauth2/v2/auth"));
    }

    @Test
    public void 네이버_로그인_시_302와_함께_로그인_페이지가_보인다 () throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorization/naver")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://nid.naver.com/oauth2.0/authorize"));
    }
    @Test
    public void 카카오_로그인_시_302와_함께_로그인_페이지가_보인다 () throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorization/kakao")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://kauth.kakao.com/oauth/authorize"));
    }
}