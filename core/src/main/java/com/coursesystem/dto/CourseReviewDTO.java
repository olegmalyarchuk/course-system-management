package com.coursesystem.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class CourseReviewDTO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String review;

    @NotNull
    private Long courseId;

    @NotNull
    private Long studentId;

}
