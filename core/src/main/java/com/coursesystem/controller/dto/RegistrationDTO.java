package com.coursesystem.controller.dto;

import com.coursesystem.model.UserRole;
import com.coursesystem.dto.UserDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = {"password"})
@NoArgsConstructor
public class RegistrationDTO extends UserDTO {

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    public RegistrationDTO(Long id, String email, String firstName,
                           String secondName, Set<UserRole> userRoles, String password) {
        super(id, email, firstName, secondName, userRoles);
        this.password = password;
    }
}
