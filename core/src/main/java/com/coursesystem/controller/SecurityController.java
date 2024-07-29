package com.coursesystem.controller;

import com.coursesystem.service.SecurityService;
import com.coursesystem.controller.dto.LoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authorize(@Valid @RequestBody final LoginDTO loginDTO) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        final String token = securityService.login(loginDTO, httpHeaders);
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }
}
