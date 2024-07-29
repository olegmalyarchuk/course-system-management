package com.coursesystem.unit.controller;

import com.coursesystem.controller.SecurityController;
import com.coursesystem.controller.dto.LoginDTO;
import com.coursesystem.service.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {SecurityController.class})
@ExtendWith(SpringExtension.class)
class SecurityControllerTest {
    @Autowired
    private SecurityController jWTLoginController;

    @MockBean
    private SecurityService securityService;

    @Test
    void testAuthorize() throws Exception {
        when(this.securityService.login((LoginDTO) any(), (org.springframework.http.HttpHeaders) any()))
                .thenReturn("Login");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("jane.doe@example.org");
        loginDTO.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.jWTLoginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Login"));
    }
}

