package com.coursesystem.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class LessonDTO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull
    private Short lessonNumber;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Short mark;

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    private Set<Long> homeworkIds;
}
