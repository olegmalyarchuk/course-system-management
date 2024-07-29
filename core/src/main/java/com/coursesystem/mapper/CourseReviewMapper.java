package com.coursesystem.mapper;

import com.coursesystem.model.CourseReview;
import com.coursesystem.dto.CourseReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface CourseReviewMapper extends EntityMapper<CourseReviewDTO, CourseReview> {

    @Override
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "studentId", target = "student.id")
    CourseReview toEntity(CourseReviewDTO dto);

    @Override
    @Mapping(source = "course", target = "courseId")
    @Mapping(source = "student.id", target = "studentId")
    CourseReviewDTO toDto(CourseReview entity);
}
