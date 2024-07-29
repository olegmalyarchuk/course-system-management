package com.coursesystem.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SystemException extends RuntimeException {
    private String message;
    private ErrorCode errorCode;
}

