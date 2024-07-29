package com.coursesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePassDTO {
    private Double finalMark;
    private boolean isCoursePassed;
    private String message;
}
