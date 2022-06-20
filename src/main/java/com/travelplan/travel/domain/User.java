package com.travelplan.travel.domain;

import com.travelplan.travel.domain.code.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.annotation.Target;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String email;
    private String userPicture;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String userName, String email, String userPicture, UserRole userRole) {
        this.userName = userName;
        this.email = email;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }

    //    @OneToMany(mappedBy = "user")
//    private List<Member> members = new ArrayList<>();
}
