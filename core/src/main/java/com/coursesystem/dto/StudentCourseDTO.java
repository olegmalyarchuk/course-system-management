package com.coursesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseDTO {

    @NotNull
    private Long studentId;
    @NotNull
    private Long courseId;
}
