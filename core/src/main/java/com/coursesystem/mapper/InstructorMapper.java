package com.coursesystem.mapper;

import com.coursesystem.model.Instructor;
import com.coursesystem.dto.InstructorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface InstructorMapper extends EntityMapper<InstructorDTO, Instructor> {

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "courses", ignore = true)
    Instructor toEntity(InstructorDTO dto);

    @Override
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.secondName", target = "secondName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "courses", target = "courses")
    InstructorDTO toDto(Instructor entity);

    default Instructor fromId(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        Instructor instructor = new Instructor();
        instructor.setId(id);
        return instructor;
    }

    default Set<Instructor> fromIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return new HashSet<>();
        }
        var instructors = ids.stream().map(this::fromId).collect(Collectors.toSet());
        return instructors;
    }

    default Long fromInstructor(Instructor instructor) {
        if (Objects.isNull(instructor)) {
            return null;
        }
        return instructor.getId();
    }

    default Set<Long> fromInstructors(Set<Instructor> instructors) {
        if (Objects.isNull(instructors) || instructors.isEmpty()) {
            return new HashSet<>();
        }
       return instructors.stream().map(this::fromInstructor).collect(Collectors.toSet());
    }
}
