package com.coursesystem.mapper;

import com.coursesystem.model.Student;
import com.coursesystem.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "courses", ignore = true)
    Student toEntity(StudentDTO dto);

    @Override
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.secondName", target = "secondName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "courses", target = "courses")
    StudentDTO toDto(Student entity);

    default Student fromId(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }

    default Set<Student> fromIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return new HashSet<>();
        }
        var students = ids.stream().map(this::fromId).collect(Collectors.toSet());
        return students;
    }

    default Long fromStudent(Student student) {
        if (Objects.isNull(student)) {
            return null;
        }
        return student.getId();
    }

    default Set<Long> fromStudents(Set<Student> students) {
        if (Objects.isNull(students) || students.isEmpty()) {
            return new HashSet<>();
        }
        return students.stream().map(this::fromStudent).collect(Collectors.toSet());
    }

}
