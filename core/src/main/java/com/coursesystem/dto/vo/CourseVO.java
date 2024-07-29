package com.coursesystem.dto.vo;

import com.coursesystem.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class CourseVO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 5)
    private Short numberOfLessons;
}
