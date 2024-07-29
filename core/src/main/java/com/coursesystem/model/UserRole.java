package com.coursesystem.model;

public enum UserRole {
    ADMIN,
    STUDENT,
    INSTRUCTOR,
    NO_ROLE;

    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}
