package com.coursesystem.mapper;

import com.coursesystem.model.Lesson;
import com.coursesystem.dto.LessonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {HomeworkMapper.class})
public interface LessonMapper extends EntityMapper<LessonDTO, Lesson> {

    @Override
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "studentId", target = "student.id")
    Lesson toEntity(LessonDTO dto);

    @Override
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "homeworks", target = "homeworkIds")
    LessonDTO toDto(Lesson lesson);
}
