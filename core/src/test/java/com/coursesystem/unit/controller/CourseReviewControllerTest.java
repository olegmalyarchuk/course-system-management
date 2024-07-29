package com.coursesystem.unit.controller;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.CourseReviewController;
import com.coursesystem.dto.CourseReviewDTO;
import com.coursesystem.model.Course;
import com.coursesystem.model.Instructor;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.CourseReviewService;
import com.coursesystem.service.impl.CourseReviewServiceImpl;
import com.coursesystem.service.impl.CourseServiceImpl;
import com.coursesystem.service.impl.StudentServiceImpl;
import com.coursesystem.service.impl.UserServiceImpl;
import com.coursesystem.repository.*;
import com.coursesystem.mapper.CourseReviewMapperImpl;
import com.coursesystem.mapper.CourseMapperImpl;
import com.coursesystem.mapper.StudentMapperImpl;
import com.coursesystem.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseReviewController.class})
@ExtendWith(SpringExtension.class)
class CourseReviewControllerTest {
    @MockBean
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private CourseReviewController courseReviewController;

    @MockBean
    private CourseReviewService courseReviewService;

    @Test
    void testCreateCourseReview() throws SystemException, URISyntaxException {


        CourseReviewRepository courseReviewRepository = mock(CourseReviewRepository.class);
        CourseReviewMapperImpl courseReviewMapper = new CourseReviewMapperImpl();
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository, courseMapper, studentRepository,
                instructorRepository);

        StudentRepository studentRepository1 = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        CourseReviewController courseReviewController = new CourseReviewController(
                new CourseReviewServiceImpl(courseReviewRepository, courseReviewMapper, courseService,
                        new StudentServiceImpl(studentRepository1, studentMapper, userService, new UserMapperImpl())),
                mock(CourseReviewRepository.class));

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> courseReviewController.createCourseReview(courseReviewDTO));
    }

    @Test
    void testCreateCourseReview2() throws SystemException, URISyntaxException {


        CourseReviewRepository courseReviewRepository = mock(CourseReviewRepository.class);
        CourseReviewMapperImpl courseReviewMapper = new CourseReviewMapperImpl();
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository, courseMapper, studentRepository,
                instructorRepository);

        StudentRepository studentRepository1 = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        CourseReviewController courseReviewController = new CourseReviewController(
                new CourseReviewServiceImpl(courseReviewRepository, courseReviewMapper, courseService,
                        new StudentServiceImpl(studentRepository1, studentMapper, userService, new UserMapperImpl())),
                mock(CourseReviewRepository.class));
        CourseReviewDTO courseReviewDTO = mock(CourseReviewDTO.class);
        when(courseReviewDTO.getId()).thenReturn(123L);
        doNothing().when(courseReviewDTO).setCreatedBy((String) any());
        doNothing().when(courseReviewDTO).setCreatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO).setUpdatedBy((String) any());
        doNothing().when(courseReviewDTO).setUpdatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO).setCourseId((Long) any());
        doNothing().when(courseReviewDTO).setReview((String) any());
        doNothing().when(courseReviewDTO).setId((Long) any());
        doNothing().when(courseReviewDTO).setStudentId((Long) any());
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> courseReviewController.createCourseReview(courseReviewDTO));
        verify(courseReviewDTO).getId();
        verify(courseReviewDTO).setCreatedBy((String) any());
        verify(courseReviewDTO).setCreatedAt((LocalDateTime) any());
        verify(courseReviewDTO).setUpdatedBy((String) any());
        verify(courseReviewDTO).setUpdatedAt((LocalDateTime) any());
        verify(courseReviewDTO).setCourseId((Long) any());
        verify(courseReviewDTO).setReview((String) any());
        verify(courseReviewDTO).setId((Long) any());
        verify(courseReviewDTO).setStudentId((Long) any());
    }

    @Test
    void testCreateCourseReview4() throws SystemException, URISyntaxException {


        Course course = new Course();
        course.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        course.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setId(123L);
        course.setInstructors(new HashSet<>());
        course.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        course.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setName("Name");
        course.setNumberOfLessons((short) 1);
        course.setStudents(new HashSet<>());
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.findById((Long) any())).thenReturn(Optional.of(course));
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        new CourseServiceImpl(courseRepository, courseMapper, studentRepository, instructorRepository);

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        CourseReviewService courseReviewService = mock(CourseReviewService.class);
        when(courseReviewService.save((CourseReviewDTO) any())).thenReturn(courseReviewDTO);
        CourseReviewController courseReviewController = new CourseReviewController(courseReviewService,
                mock(CourseReviewRepository.class));
        CourseReviewDTO courseReviewDTO1 = mock(CourseReviewDTO.class);
        when(courseReviewDTO1.getCourseId()).thenReturn(123L);
        when(courseReviewDTO1.getId()).thenReturn(null);
        doNothing().when(courseReviewDTO1).setCreatedBy((String) any());
        doNothing().when(courseReviewDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO1).setUpdatedBy((String) any());
        doNothing().when(courseReviewDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO1).setCourseId((Long) any());
        doNothing().when(courseReviewDTO1).setReview((String) any());
        doNothing().when(courseReviewDTO1).setId((Long) any());
        doNothing().when(courseReviewDTO1).setStudentId((Long) any());
        courseReviewDTO1.setCourseId(123L);
        courseReviewDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setReview("Review");
        courseReviewDTO1.setId(123L);
        courseReviewDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setStudentId(123L);
        ResponseEntity<CourseReviewDTO> actualCreateCourseReviewResult = courseReviewController
                .createCourseReview(courseReviewDTO1);
        assertTrue(actualCreateCourseReviewResult.hasBody());
        assertEquals(3, actualCreateCourseReviewResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateCourseReviewResult.getStatusCode());
        verify(courseReviewService).save((CourseReviewDTO) any());
        verify(courseReviewDTO1).getId();
        verify(courseReviewDTO1).setCreatedBy((String) any());
        verify(courseReviewDTO1).setCreatedAt((LocalDateTime) any());
        verify(courseReviewDTO1).setUpdatedBy((String) any());
        verify(courseReviewDTO1).setUpdatedAt((LocalDateTime) any());
        verify(courseReviewDTO1).setCourseId((Long) any());
        verify(courseReviewDTO1).setReview((String) any());
        verify(courseReviewDTO1).setId((Long) any());
        verify(courseReviewDTO1).setStudentId((Long) any());
    }

    @Test
    void testCreateCourseReview5() throws SystemException, URISyntaxException {


        HashSet<Instructor> instructors = new HashSet<>();

        Course course = new Course(123L, "REST request to save CourseReview : {}", (short) 4, instructors,
                new HashSet<>());
        course.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        course.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setId(123L);
        course.setInstructors(new HashSet<>());
        course.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        course.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setName("Name");
        course.setNumberOfLessons((short) 1);
        course.setStudents(new HashSet<>());
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.findById((Long) any())).thenReturn(Optional.of(course));
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        new CourseServiceImpl(courseRepository, courseMapper, studentRepository, instructorRepository);

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        CourseReviewService courseReviewService = mock(CourseReviewService.class);
        when(courseReviewService.save((CourseReviewDTO) any())).thenReturn(courseReviewDTO);
        CourseReviewController courseReviewController = new CourseReviewController(courseReviewService,
                mock(CourseReviewRepository.class));
        CourseReviewDTO courseReviewDTO1 = mock(CourseReviewDTO.class);
        when(courseReviewDTO1.getCourseId()).thenReturn(123L);
        when(courseReviewDTO1.getId()).thenReturn(null);
        doNothing().when(courseReviewDTO1).setCreatedBy((String) any());
        doNothing().when(courseReviewDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO1).setUpdatedBy((String) any());
        doNothing().when(courseReviewDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(courseReviewDTO1).setCourseId((Long) any());
        doNothing().when(courseReviewDTO1).setReview((String) any());
        doNothing().when(courseReviewDTO1).setId((Long) any());
        doNothing().when(courseReviewDTO1).setStudentId((Long) any());
        courseReviewDTO1.setCourseId(123L);
        courseReviewDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setReview("Review");
        courseReviewDTO1.setId(123L);
        courseReviewDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setStudentId(123L);
        ResponseEntity<CourseReviewDTO> actualCreateCourseReviewResult = courseReviewController
                .createCourseReview(courseReviewDTO1);
        assertTrue(actualCreateCourseReviewResult.hasBody());
        assertEquals(3, actualCreateCourseReviewResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateCourseReviewResult.getStatusCode());
        verify(courseReviewService).save((CourseReviewDTO) any());
        verify(courseReviewDTO1).getId();
        verify(courseReviewDTO1).setCreatedBy((String) any());
        verify(courseReviewDTO1).setCreatedAt((LocalDateTime) any());
        verify(courseReviewDTO1).setUpdatedBy((String) any());
        verify(courseReviewDTO1).setUpdatedAt((LocalDateTime) any());
        verify(courseReviewDTO1).setCourseId((Long) any());
        verify(courseReviewDTO1).setReview((String) any());
        verify(courseReviewDTO1).setId((Long) any());
        verify(courseReviewDTO1).setStudentId((Long) any());
    }

    @Test
    void testUpdateCourseReview() {


        CourseReviewRepository courseReviewRepository = mock(CourseReviewRepository.class);
        CourseReviewMapperImpl courseReviewMapper = new CourseReviewMapperImpl();
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository, courseMapper, studentRepository,
                instructorRepository);

        StudentRepository studentRepository1 = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        CourseReviewController courseReviewController = new CourseReviewController(
                new CourseReviewServiceImpl(courseReviewRepository, courseReviewMapper, courseService,
                        new StudentServiceImpl(studentRepository1, studentMapper, userService, new UserMapperImpl())),
                mock(CourseReviewRepository.class));

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> courseReviewController.updateCourseReview(1L, courseReviewDTO));
    }

    @Test
    void testUpdateCourseReview5() {


        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        CourseReviewServiceImpl courseReviewServiceImpl = mock(CourseReviewServiceImpl.class);
        when(courseReviewServiceImpl.save((CourseReviewDTO) any())).thenReturn(courseReviewDTO);
        CourseReviewRepository courseReviewRepository = mock(CourseReviewRepository.class);
        when(courseReviewRepository.existsById((Long) any())).thenReturn(true);
        CourseReviewController courseReviewController = new CourseReviewController(courseReviewServiceImpl,
                courseReviewRepository);

        CourseReviewDTO courseReviewDTO1 = new CourseReviewDTO();
        courseReviewDTO1.setCourseId(123L);
        courseReviewDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setReview("Review");
        courseReviewDTO1.setId(1L);
        courseReviewDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setStudentId(123L);
        ResponseEntity<CourseReviewDTO> actualUpdateCourseReviewResult = courseReviewController.updateCourseReview(1L,
                courseReviewDTO1);
        assertTrue(actualUpdateCourseReviewResult.hasBody());
        assertEquals(2, actualUpdateCourseReviewResult.getHeaders().size());
        assertEquals(HttpStatus.OK, actualUpdateCourseReviewResult.getStatusCode());
        verify(courseReviewServiceImpl).save((CourseReviewDTO) any());
        verify(courseReviewRepository).existsById((Long) any());
    }

    @Test
    void testDeleteCourseReview() throws Exception {
        doNothing().when(this.courseReviewService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/course-review/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteCourseReview2() throws Exception {
        doNothing().when(this.courseReviewService).delete((Long) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteCourseReview3() throws Exception {
        doNothing().when(this.courseReviewService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/course-review/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllCourseReviews() throws Exception {
        when(this.courseReviewService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/course-reviews");
        MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllCourseReviews2() throws Exception {
        when(this.courseReviewService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/course-reviews");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetCourseReview() throws Exception {
        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        Optional<CourseReviewDTO> ofResult = Optional.of(courseReviewDTO);
        when(this.courseReviewService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/course-review/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1"
                                        + "],\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"review\":\"Review\",\"courseId\":123,\"studentId"
                                        + "\":123}"));
    }

    @Test
    void testGetCourseReview2() throws Exception {
        when(this.courseReviewService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/course-review/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetCourseReview3() throws Exception {
        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        Optional<CourseReviewDTO> ofResult = Optional.of(courseReviewDTO);
        when(this.courseReviewService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/course-review/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.courseReviewController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1"
                                        + "],\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"review\":\"Review\",\"courseId\":123,\"studentId"
                                        + "\":123}"));
    }
}

