package com.coursesystem.configuration.exception;

import lombok.Data;

@Data
public class ApiExceptionDTO {
    private String message;
    private String code;

}
