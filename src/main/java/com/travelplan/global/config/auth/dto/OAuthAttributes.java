package com.travelplan.global.config.auth.dto;

import com.travelplan.domain.user.domain.User;
import com.travelplan.global.entity.code.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.travelplan.global.config.auth.AttributeCodes.*;

@Slf4j
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String ,Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id",attributes);
        }
        if ("kakao".equals(registrationId)) {
            log.info("userNameAttributeName = {} " , userNameAttributeName);
            return ofKakao("id", attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get(KAKAO.getName()))
                .email((String) kakaoAccount.get(KAKAO.getEmail()))
                .picture((String) kakaoProfile.get(KAKAO.getPicture()))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get(NAVER.getName()))
                .email((String) response.get(NAVER.getEmail()))
                .picture((String) response.get(NAVER.getPicture()))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get(GOOGLE.getName()))
                .email((String) attributes.get(GOOGLE.getEmail()))
                .picture((String) attributes.get(GOOGLE.getPicture()))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity() {
        return User.builder()
                .userName(name)
                .email(email)
                .userPicture(picture)
                .userRole(UserRole.USER)
                .build();
    }
}
