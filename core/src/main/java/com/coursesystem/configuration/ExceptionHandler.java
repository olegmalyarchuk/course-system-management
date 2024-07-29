package com.coursesystem.configuration;

import com.coursesystem.configuration.exception.ApiExceptionDTO;
import com.coursesystem.configuration.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionDTO handleCustomException(final SQLException ex) {
        log.error(ex.getMessage(), ex);
        var apiExceptionDTO = new ApiExceptionDTO();
        apiExceptionDTO.setMessage(ex.getMessage());
        apiExceptionDTO.setCode(String.valueOf(ex.getErrorCode()));
        return apiExceptionDTO;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SystemException.class)
    public ResponseEntity<ApiExceptionDTO> handleCustomException(final SystemException ex) {
        log.error(ex.getMessage(), ex);
        var builder = ResponseEntity.status(ex.getErrorCode().getHttpStatus());
        var apiExceptionDTO = new ApiExceptionDTO();
        apiExceptionDTO.setMessage(ex.getMessage());
        apiExceptionDTO.setCode(ex.getErrorCode().getHttpStatus().name());
        return builder.body(apiExceptionDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionDTO> handleCustomException(final MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        var builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        var apiExceptionDTO = new ApiExceptionDTO();
        apiExceptionDTO.setMessage(ex.getMessage());
        apiExceptionDTO.setCode(HttpStatus.BAD_REQUEST.name());
        return builder.body(apiExceptionDTO);
    }
}
