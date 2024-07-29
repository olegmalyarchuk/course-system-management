package com.coursesystem.integration;

import com.coursesystem.Application;
import com.coursesystem.util.TestUtil;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class, TestUtil.class})
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "admin", authorities = { "ADMIN" })
public @interface IntegrationTest {
}
