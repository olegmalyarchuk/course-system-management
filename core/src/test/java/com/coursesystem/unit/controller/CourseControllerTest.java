package com.coursesystem.unit.controller;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.CourseController;
import com.coursesystem.dto.CourseDTO;
import com.coursesystem.model.Course;
import com.coursesystem.model.Instructor;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.service.CourseService;
import com.coursesystem.service.impl.CourseServiceImpl;
import com.coursesystem.mapper.CourseMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
class CourseControllerTest {
    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseController courseController;

    @MockBean
    private CourseService courseService;

    @Test
    void testAddInstructorToCourse() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/course/{courseId}/add-instructor",
                "Uri Vars", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testCreateCourse() throws SystemException, URISyntaxException {
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseController courseController = new CourseController(new CourseServiceImpl(courseRepository, courseMapper,
                studentRepository, instructorRepository), mock(CourseRepository.class));

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        assertThrows(SystemException.class, () -> courseController.createCourse(courseDTO));
    }

    @Test
    void testCreateCourse2() throws SystemException, URISyntaxException {
        Course course = mock(Course.class);
        when(course.getStudents()).thenReturn(new HashSet<>());
        doNothing().when(course).setCreatedBy((String) any());
        doNothing().when(course).setCreatedAt((LocalDateTime) any());
        doNothing().when(course).setUpdatedBy((String) any());
        doNothing().when(course).setUpdatedAt((LocalDateTime) any());
        doNothing().when(course).setId((Long) any());
        doNothing().when(course).setInstructors((Set<Instructor>) any());
        doNothing().when(course).setName((String) any());
        doNothing().when(course).setNumberOfLessons((Short) any());
        doNothing().when(course).setStudents((Set<Student>) any());
        course.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        course.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setId(123L);
        course.setInstructors(new HashSet<>());
        course.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        course.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course.setName("Name");
        course.setNumberOfLessons((short) 1);
        course.setStudents(new HashSet<>());
        Optional<Course> ofResult = Optional.of(course);

        Course course1 = new Course();
        course1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        course1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course1.setId(123L);
        course1.setInstructors(new HashSet<>());
        course1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        course1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course1.setName("Name");
        course1.setNumberOfLessons((short) 1);
        course1.setStudents(new HashSet<>());
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.save((Course) any())).thenReturn(course1);
        when(courseRepository.findById((Long) any())).thenReturn(ofResult);

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());

        User user = new User();
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        user.setUserRoles("User Roles");

        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructor.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setId(123L);
        instructor.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructor.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setUser(user);

        HashSet<Instructor> instructorSet = new HashSet<>();
        instructorSet.add(instructor);
        Course course2 = mock(Course.class);
        when(course2.getStudents()).thenReturn(new HashSet<>());
        when(course2.getInstructors()).thenReturn(instructorSet);
        doNothing().when(course2).setCreatedBy((String) any());
        doNothing().when(course2).setCreatedAt((LocalDateTime) any());
        doNothing().when(course2).setUpdatedBy((String) any());
        doNothing().when(course2).setUpdatedAt((LocalDateTime) any());
        doNothing().when(course2).setId((Long) any());
        doNothing().when(course2).setInstructors((Set<Instructor>) any());
        doNothing().when(course2).setName((String) any());
        doNothing().when(course2).setNumberOfLessons((Short) any());
        doNothing().when(course2).setStudents((Set<Student>) any());
        course2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        course2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course2.setId(123L);
        course2.setInstructors(new HashSet<>());
        course2.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        course2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        course2.setName("Name");
        course2.setNumberOfLessons((short) 1);
        course2.setStudents(new HashSet<>());
        CourseMapperImpl courseMapperImpl = mock(CourseMapperImpl.class);
        when(courseMapperImpl.toEntity((CourseDTO) any())).thenReturn(course2);
        when(courseMapperImpl.toDto((Course) any())).thenReturn(courseDTO);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        when(instructorRepository.existsById((Long) any())).thenReturn(true);
        StudentRepository studentRepository = mock(StudentRepository.class);

        CourseController courseController = new CourseController(new CourseServiceImpl(courseRepository, courseMapperImpl,
                studentRepository, instructorRepository), mock(CourseRepository.class));
        courseController.addStudentToCourse(123L, new HashSet<>());

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setId(123L);
        courseDTO1.setInstructorIds(new HashSet<>());
        courseDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setName("Name");
        courseDTO1.setNumberOfLessons((short) 1);
        courseDTO1.setStudentIds(new HashSet<>());
        assertThrows(SystemException.class, () -> courseController.createCourse(courseDTO1));
        verify(courseRepository).save((Course) any());
        verify(courseRepository).findById((Long) any());
        verify(course).setCreatedBy((String) any());
        verify(course).setCreatedAt((LocalDateTime) any());
        verify(course).setUpdatedBy((String) any());
        verify(course).setUpdatedAt((LocalDateTime) any());
        verify(course).setId((Long) any());
        verify(course).setInstructors((Set<Instructor>) any());
        verify(course).setName((String) any());
        verify(course).setNumberOfLessons((Short) any());
        verify(course).setStudents((Set<Student>) any());
        verify(courseMapperImpl).toEntity((CourseDTO) any());
        verify(courseMapperImpl, atLeast(1)).toDto((Course) any());
        verify(course2).getInstructors();
        verify(course2).getStudents();
        verify(course2).setCreatedBy((String) any());
        verify(course2).setCreatedAt((LocalDateTime) any());
        verify(course2).setUpdatedBy((String) any());
        verify(course2).setUpdatedAt((LocalDateTime) any());
        verify(course2).setId((Long) any());
        verify(course2).setInstructors((Set<Instructor>) any());
        verify(course2).setName((String) any());
        verify(course2).setNumberOfLessons((Short) any());
        verify(course2).setStudents((Set<Student>) any());
        verify(instructorRepository).existsById((Long) any());
    }

    @Test
    void testUpdateCourse() {
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseMapperImpl courseMapper = new CourseMapperImpl();
        StudentRepository studentRepository = mock(StudentRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseController courseController = new CourseController(new CourseServiceImpl(courseRepository, courseMapper,
                studentRepository, instructorRepository), mock(CourseRepository.class));

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        assertThrows(SystemException.class, () -> courseController.updateCourse(1L, courseDTO));
    }

    @Test
    void testUpdateCourse5() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        CourseService courseService = mock(CourseService.class);
        when(courseService.save((CourseDTO) any())).thenReturn(courseDTO);
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.existsById((Long) any())).thenReturn(true);
        CourseController courseController = new CourseController(courseService, courseRepository);

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setId(1L);
        courseDTO1.setInstructorIds(new HashSet<>());
        courseDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setName("Name");
        courseDTO1.setNumberOfLessons((short) 1);
        courseDTO1.setStudentIds(new HashSet<>());
        ResponseEntity<CourseDTO> actualUpdateCourseResult = courseController.updateCourse(1L, courseDTO1);
        assertTrue(actualUpdateCourseResult.hasBody());
        assertEquals(2, actualUpdateCourseResult.getHeaders().size());
        assertEquals(HttpStatus.OK, actualUpdateCourseResult.getStatusCode());
        verify(courseService).save((CourseDTO) any());
        verify(courseRepository).existsById((Long) any());
    }

    @Test
    void testUpdateCourse6() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        CourseService courseService = mock(CourseService.class);
        when(courseService.save((CourseDTO) any())).thenReturn(courseDTO);
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.existsById((Long) any())).thenReturn(false);
        CourseController courseController = new CourseController(courseService, courseRepository);

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setId(1L);
        courseDTO1.setInstructorIds(new HashSet<>());
        courseDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setName("Name");
        courseDTO1.setNumberOfLessons((short) 1);
        courseDTO1.setStudentIds(new HashSet<>());
        assertThrows(SystemException.class, () -> courseController.updateCourse(1L, courseDTO1));
        verify(courseRepository).existsById((Long) any());
    }

    @Test
    void testUpdateCourse7() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        CourseService courseService = mock(CourseService.class);
        when(courseService.save((CourseDTO) any())).thenReturn(courseDTO);
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.existsById((Long) any())).thenReturn(true);
        CourseController courseController = new CourseController(courseService, courseRepository);

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setId(null);
        courseDTO1.setInstructorIds(new HashSet<>());
        courseDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO1.setName("Name");
        courseDTO1.setNumberOfLessons((short) 1);
        courseDTO1.setStudentIds(new HashSet<>());
        assertThrows(SystemException.class, () -> courseController.updateCourse(1L, courseDTO1));
    }

    @Test
    void testAddInstructorToCourse2() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAddStudentToCourse() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/course/{courseId}/add-student",
                "Uri Vars", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(this.courseService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/courses/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteCourse2() throws Exception {
        doNothing().when(this.courseService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/courses/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllCourses() throws Exception {
        when(this.courseService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/courses");
        MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllCourses2() throws Exception {
        when(this.courseService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/courses");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllStudentsPerCourse() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/courses/students");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testGetCourse() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        Optional<CourseDTO> ofResult = Optional.of(courseDTO);
        when(this.courseService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/courses/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"name\":\"Name\",\"numberOfLessons\":1,\"instructorIds\""
                                        + ":[],\"studentIds\":[]}"));
    }

    @Test
    void testGetCourse2() throws Exception {
        when(this.courseService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetCourse3() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setId(123L);
        courseDTO.setInstructorIds(new HashSet<>());
        courseDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseDTO.setName("Name");
        courseDTO.setNumberOfLessons((short) 1);
        courseDTO.setStudentIds(new HashSet<>());
        Optional<CourseDTO> ofResult = Optional.of(courseDTO);
        when(this.courseService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/courses/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"name\":\"Name\",\"numberOfLessons\":1,\"instructorIds\""
                                        + ":[],\"studentIds\":[]}"));
    }
}

