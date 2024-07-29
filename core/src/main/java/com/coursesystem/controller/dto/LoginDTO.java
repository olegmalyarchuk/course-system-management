package com.coursesystem.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    public LoginDTO(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
