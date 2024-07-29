package com.coursesystem.dto;

import com.coursesystem.dto.vo.CourseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class InstructorDTO extends BaseDTO implements Serializable {

    private Long id;

    private String email;

    @Size(min = 0, max = 40)
    private String firstName;

    @Size(min = 0, max = 40)
    private String secondName;

    @NotNull
    private Long userId;
    private Set<CourseVO> courses;
}
