package com.travelplan.global.config.auth.oauth2.session;

import com.travelplan.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Integer currentTravelId;

    public SessionUser(User user) {
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.picture = user.getUserPicture();
    }
}
