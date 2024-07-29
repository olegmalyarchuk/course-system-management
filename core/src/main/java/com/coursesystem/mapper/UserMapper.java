package com.coursesystem.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.util.JsonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Override
    @Mapping(target = "userRoles", source = "userRoles", qualifiedByName = "serializeUserRoles")
    User toEntity(UserDTO dto);

    @Override
    @Mapping(target = "userRoles", source = "userRoles", qualifiedByName = "deserializeUserRoles")
    UserDTO toDto(User entity);

    default User fromId(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("serializeUserRoles")
    default String serializeUserRoles(final Set<UserRole> userRoles) {
        return JsonUtil.serialize(userRoles);
    }

    @Named("deserializeUserRoles")
    default Set<UserRole> deserializeUserRoles(final String userRoles) {
        return JsonUtil.deserialize(userRoles, new TypeReference<Set<UserRole>>() {
        });
    }
}
