package com.coursesystem.mapper;

import com.coursesystem.model.Course;
import com.coursesystem.dto.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, InstructorMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Override
    @Mapping(source = "studentIds", target = "students")
    @Mapping(source = "instructorIds", target = "instructors")
    Course toEntity(CourseDTO dto);

    @Override
    @Mapping(source = "students", target = "studentIds")
    @Mapping(source = "instructors", target = "instructorIds")
    CourseDTO toDto(Course entity);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }

    default Set<Course> fromIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return new HashSet<>();
        }
       return ids.stream().map(this::fromId).collect(Collectors.toSet());
    }

    default Long fromCourse(Course course) {
        if (course == null) {
            return null;
        }
        return course.getId();
    }

    default Set<Long> fromCourses(Set<Course> courses) {
        if (Objects.isNull(courses) || courses.isEmpty()) {
            return new HashSet<>();
        }
        return courses.stream().map(this::fromCourse).collect(Collectors.toSet());
    }

}
