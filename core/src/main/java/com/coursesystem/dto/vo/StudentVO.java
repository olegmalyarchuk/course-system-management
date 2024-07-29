package com.coursesystem.dto.vo;

import com.coursesystem.model.Student;
import com.coursesystem.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class StudentVO extends BaseDTO implements Serializable {

    public StudentVO(Student student) {
        super(student.getUpdatedAt(), student.getUpdatedBy(), student.getCreatedAt(), student.getCreatedBy());
        this.id = student.getId();
        this.email = student.getUser().getEmail();
        this.firstName = student.getUser().getFirstName();
        this.secondName = student.getUser().getSecondName();
    }

    private Long id;

    private String email;

    @Size(min = 0, max = 40)
    private String firstName;

    @Size(min = 0, max = 40)
    private String secondName;

}
