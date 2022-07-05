package com.travelplan.domain.user.domain;

import com.travelplan.global.entity.base.BaseDateEntity;
import com.travelplan.global.entity.code.UserRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@ToString(exclude = "email")
public class User extends BaseDateEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String email;
    private String userPicture;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(String userName, String email, String userPicture, UserRole userRole) {
        this.userName = userName;
        this.email = email;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }

    public User update(String name, String picture) {
        userName = name;
        userPicture = picture;
        return this;
    }

    //    @OneToMany(mappedBy = "user")
//    private List<Member> members = new ArrayList<>();
}
