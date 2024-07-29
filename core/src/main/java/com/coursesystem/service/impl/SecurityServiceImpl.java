package com.coursesystem.service.impl;

import com.coursesystem.service.SecurityService;
import com.coursesystem.service.UserService;
import com.coursesystem.configuration.security.filter.JwtTokenFilter;
import com.coursesystem.configuration.security.TokenProvider;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.controller.dto.LoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {


    private final TokenProvider tokenProvider;
    private final UserService userService;


    public SecurityServiceImpl(final TokenProvider tokenProvider, final UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    public String login(final LoginDTO loginDTO, final HttpHeaders httpHeaders) {
        final UserDTO userDTO = userService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        final String token = "Bearer " + tokenProvider.generateToken(userDTO.getEmail());
        httpHeaders.add(JwtTokenFilter.AUTHORIZATION_HEADER, token);
        return token;
    }

    @Override
    public UserDTO getAuthenticatedUser(HttpHeaders httpHeaders) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDTO userDTO = userService.findByEmail(authentication.getPrincipal().toString());
        return userDTO;
    }
}
