package com.coursesystem.service;

import com.coursesystem.dto.UserDTO;
import com.coursesystem.controller.dto.LoginDTO;
import org.springframework.http.HttpHeaders;

public interface SecurityService {

    String login(final LoginDTO loginDTO, final HttpHeaders httpHeaders);

    UserDTO getAuthenticatedUser(HttpHeaders httpHeaders);

}
