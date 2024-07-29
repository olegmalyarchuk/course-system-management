package com.coursesystem.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class HomeworkDTO extends BaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String originalFileName;

    private String storedFileName;

    @NotNull
    private Long lessonId;
}
