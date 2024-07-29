package com.coursesystem.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ResponseUtil {
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> optionalResponse) {
        return wrapOrNotFound(optionalResponse, (HttpHeaders)null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> optionalResponse, HttpHeaders header) {
        return (ResponseEntity)optionalResponse.map((response) -> {
            return ((BodyBuilder)ResponseEntity.ok().headers(header)).body(response);
        }).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }
}