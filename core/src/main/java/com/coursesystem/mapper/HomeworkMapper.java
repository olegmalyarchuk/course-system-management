package com.coursesystem.mapper;

import com.coursesystem.model.Homework;
import com.coursesystem.dto.HomeworkDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {})
public interface HomeworkMapper extends EntityMapper<HomeworkDTO, Homework> {

    @Override
    @Mapping(source = "lessonId", target = "lesson.id")
    Homework toEntity(HomeworkDTO dto);

    @Override
    @Mapping(source = "lesson.id", target = "lessonId")
    HomeworkDTO toDto(Homework attachment);

    @Named(value = "fromHomeworks")
    default Collection<Long> fromHomeworks(Collection<Homework> homework) {
        if (Objects.isNull(homework) || homework.isEmpty()) {
            return new HashSet<>();
        }
        var ids = homework.stream().map(this::fromHomework).collect(Collectors.toSet());
        return ids;
    }

    default Long fromHomework(Homework homework) {
        if (Objects.isNull(homework)) {
            return null;
        }
        return homework.getId();
    }
}
