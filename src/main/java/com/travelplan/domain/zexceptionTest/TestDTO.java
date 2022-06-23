package com.travelplan.domain.zexceptionTest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestDTO {

    private String id;

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 5, max = 10)
    private String phone;

    public TestDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public TestDTO(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
