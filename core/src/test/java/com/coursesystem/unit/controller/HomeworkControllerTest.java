package com.coursesystem.unit.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.coursesystem.configuration.CourseProperties;
import com.coursesystem.controller.HomeworkController;
import com.coursesystem.dto.HomeworkDTO;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.HomeworkRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.HomeworkService;
import com.coursesystem.service.impl.CourseServiceImpl;
import com.coursesystem.service.impl.HomeworkServiceImpl;
import com.coursesystem.service.impl.LessonServiceImpl;
import com.coursesystem.service.impl.StudentServiceImpl;
import com.coursesystem.service.impl.UserServiceImpl;
import com.coursesystem.repository.*;
import com.coursesystem.service.impl.*;
import com.coursesystem.mapper.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {HomeworkController.class})
@ExtendWith(SpringExtension.class)
class HomeworkControllerTest {
    @Autowired
    private HomeworkController homeworkController;

    @MockBean
    private HomeworkService homeworkService;

    @Test
    void testDeleteFile2() throws SdkClientException {
        AmazonS3Client amazonS3Client = mock(AmazonS3Client.class);
        doNothing().when(amazonS3Client).deleteObject((String) any(), (String) any());
        LessonRepository lessonRepository = mock(LessonRepository.class);
        LessonMapperImpl lessonMapper = new LessonMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentServiceImpl studentService = new StudentServiceImpl(studentRepository, studentMapper, userService,
                new UserMapperImpl());

        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository1 = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseProperties courseProperties = mock(CourseProperties.class);
        LessonServiceImpl lessonService = new LessonServiceImpl(lessonRepository, lessonMapper, studentService,
                new CourseServiceImpl(courseRepository, courseMapper, studentRepository1, instructorRepository), courseProperties);

        LessonMapperImpl lessonMapper1 = new LessonMapperImpl();
        HomeworkController homeworkController = new HomeworkController(
                new HomeworkServiceImpl(amazonS3Client, lessonService, lessonMapper1,
                        new HomeworkMapperImpl(), mock(HomeworkRepository.class)));

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        ResponseEntity<String> actualDeleteFileResult = homeworkController.deleteFile(homeworkDTO);
        assertEquals("foo.txt removed ...", actualDeleteFileResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteFileResult.getStatusCode());
        assertTrue(actualDeleteFileResult.getHeaders().isEmpty());
        verify(amazonS3Client).deleteObject((String) any(), (String) any());
    }

    @Test
    void testUploadFile() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/homework/upload/{lessonId}",
                "Uri Vars", "Uri Vars");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("file", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.homeworkController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

