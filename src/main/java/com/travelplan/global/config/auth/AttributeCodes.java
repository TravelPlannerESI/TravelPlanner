package com.travelplan.global.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum AttributeCodes {
    KAKAO("nickname","account_email","profile_image_url"),
    NAVER("name","email","profile_image"),
    GOOGLE("name","eamil","picture");

    private final String name;
    private final String email;
    private final String picture;

}
