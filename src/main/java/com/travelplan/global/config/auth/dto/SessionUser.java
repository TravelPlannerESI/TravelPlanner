package com.travelplan.global.config.auth.dto;

import com.travelplan.domain.user.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.picture = user.getUserPicture();
    }
}
