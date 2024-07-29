package com.coursesystem.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class CourseDTO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 5)
    private Short numberOfLessons;

    @NotNull
    private Set<Long> instructorIds = new HashSet<>();

    private Set<Long> studentIds = new HashSet<>();

}
