package com.coursesystem.unit.controller;

import com.coursesystem.configuration.CourseProperties;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.LessonController;
import com.coursesystem.dto.CoursePassDTO;
import com.coursesystem.dto.LessonDTO;
import com.coursesystem.dto.StudentCourseDTO;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.LessonService;
import com.coursesystem.service.impl.CourseServiceImpl;
import com.coursesystem.service.impl.LessonServiceImpl;
import com.coursesystem.service.impl.StudentServiceImpl;
import com.coursesystem.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coursesystem.repository.*;
import com.coursesystem.mapper.CourseMapperImpl;
import com.coursesystem.mapper.LessonMapperImpl;
import com.coursesystem.mapper.StudentMapperImpl;
import com.coursesystem.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {LessonController.class})
@ExtendWith(SpringExtension.class)
class LessonControllerTest {
    @MockBean
    private LessonRepository lessonRepository;

    @Autowired
    private LessonController lessonController;

    @MockBean
    private LessonService lessonService;

    @Test
    void testCreateLesson() throws URISyntaxException {
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
        LessonController lessonController = new LessonController(
                new LessonServiceImpl(lessonRepository, lessonMapper, studentService, new CourseServiceImpl(courseRepository,
                        courseMapper, studentRepository1, instructorRepository), courseProperties),
                mock(LessonRepository.class));

        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setCourseId(123L);
        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setId(123L);
        lessonDTO.setLessonNumber((short) 1);
        lessonDTO.setMark((short) 1);
        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setHomeworkIds(new HashSet<>());
        lessonDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> lessonController.createLesson(lessonDTO));
    }

    @Test
    void testCreateLesson2() throws URISyntaxException {
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
        LessonController lessonController = new LessonController(
                new LessonServiceImpl(lessonRepository, lessonMapper, studentService, new CourseServiceImpl(courseRepository,
                        courseMapper, studentRepository1, instructorRepository), courseProperties),
                mock(LessonRepository.class));
        LessonDTO lessonDTO = mock(LessonDTO.class);
        when(lessonDTO.getId()).thenReturn(123L);
        doNothing().when(lessonDTO).setCreatedBy((String) any());
        doNothing().when(lessonDTO).setCreatedAt((LocalDateTime) any());
        doNothing().when(lessonDTO).setUpdatedBy((String) any());
        doNothing().when(lessonDTO).setUpdatedAt((LocalDateTime) any());
        doNothing().when(lessonDTO).setCourseId((Long) any());
        doNothing().when(lessonDTO).setId((Long) any());
        doNothing().when(lessonDTO).setLessonNumber((Short) any());
        doNothing().when(lessonDTO).setMark((Short) any());
        doNothing().when(lessonDTO).setHomeworkIds((java.util.Set<Long>) any());
        doNothing().when(lessonDTO).setStudentId((Long) any());
        lessonDTO.setCourseId(123L);
        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setId(123L);
        lessonDTO.setLessonNumber((short) 1);
        lessonDTO.setMark((short) 1);
        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setHomeworkIds(new HashSet<>());
        lessonDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> lessonController.createLesson(lessonDTO));
        verify(lessonDTO).getId();
        verify(lessonDTO).setCreatedBy((String) any());
        verify(lessonDTO).setCreatedAt((LocalDateTime) any());
        verify(lessonDTO).setUpdatedBy((String) any());
        verify(lessonDTO).setUpdatedAt((LocalDateTime) any());
        verify(lessonDTO).setCourseId((Long) any());
        verify(lessonDTO).setId((Long) any());
        verify(lessonDTO).setLessonNumber((Short) any());
        verify(lessonDTO).setMark((Short) any());
        verify(lessonDTO).setHomeworkIds((java.util.Set<Long>) any());
        verify(lessonDTO).setStudentId((Long) any());
    }

//    @Test
//    void testCreateLesson3() throws URISyntaxException {
//        Course course = new Course();
//        course.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        course.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        course.setId(123L);
//        course.setInstructors(new HashSet<>());
//        course.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
//        course.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        course.setName("Name");
//        course.setNumberOfLessons((short) 1);
//        course.setStudents(new HashSet<>());
//
//        User user = new User();
//        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        user.setEmail("jane.doe@example.org");
//        user.setFirstName("Jane");
//        user.setId(123L);
//        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
//        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        user.setPassword("iloveyou");
//        user.setSecondName("Second Name");
//        user.setUserRoles("User Roles");
//
//        Student student = new Student();
//        student.setCourses(new HashSet<>());
//        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        student.setId(123L);
//        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
//        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        student.setUser(user);
//
//        Lesson lesson = new Lesson();
//        lesson.setCourse(course);
//        lesson.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        lesson.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        lesson.setId(123L);
//        lesson.setLessonNumber((short) 1);
//        lesson.setMark((short) 1);
//        lesson.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
//        lesson.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        lesson.setStudent(student);
//        lesson.setHomeworks(new HashSet<>());
//        LessonRepository lessonRepository = mock(LessonRepository.class);
//        when(lessonRepository.save((Lesson) any())).thenReturn(lesson);
//        LessonMapperImpl lessonMapper = new LessonMapperImpl();
//        StudentRepository studentRepository = mock(StudentRepository.class);
//        StudentMapperImpl studentMapper = new StudentMapperImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());
//
//        StudentServiceImpl studentService = new StudentServiceImpl(studentRepository, studentMapper, userService,
//                new UserMapperImpl());
//
//        CourseRepository courseRepository = mock(CourseRepository.class);
//        CourseMapperImpl courseMapper = new CourseMapperImpl();
//        StudentRepository studentRepository1 = mock(StudentRepository.class);
//        InstructorRepository instructorRepository = mock(InstructorRepository.class);
//        CourseProperties courseProperties = mock(CourseProperties.class);
//        LessonController lessonController = new LessonController(
//                new LessonServiceImpl(lessonRepository, lessonMapper, studentService, new CourseServiceImpl(courseRepository,
//                        courseMapper, studentRepository1, instructorRepository), courseProperties),
//                mock(LessonRepository.class));
//        LessonDTO lessonDTO = mock(LessonDTO.class);
//        when(lessonDTO.getCourseId()).thenReturn(123L);
//        when(lessonDTO.getStudentId()).thenReturn(123L);
//        when(lessonDTO.getLessonNumber()).thenReturn((short) 1);
//        when(lessonDTO.getMark()).thenReturn((short) 1);
//        when(lessonDTO.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
//        when(lessonDTO.getUpdatedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
//        when(lessonDTO.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
//        when(lessonDTO.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
//        when(lessonDTO.getId()).thenReturn(null);
//        doNothing().when(lessonDTO).setCreatedBy((String) any());
//        doNothing().when(lessonDTO).setCreatedAt((LocalDateTime) any());
//        doNothing().when(lessonDTO).setUpdatedBy((String) any());
//        doNothing().when(lessonDTO).setUpdatedAt((LocalDateTime) any());
//        doNothing().when(lessonDTO).setCourseId((Long) any());
//        doNothing().when(lessonDTO).setId((Long) any());
//        doNothing().when(lessonDTO).setLessonNumber((Short) any());
//        doNothing().when(lessonDTO).setMark((Short) any());
//        doNothing().when(lessonDTO).setHomeworkIds((Set<Long>) any());
//        doNothing().when(lessonDTO).setStudentId((Long) any());
//        lessonDTO.setCourseId(123L);
//        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        lessonDTO.setId(123L);
//        lessonDTO.setLessonNumber((short) 1);
//        lessonDTO.setMark((short) 1);
//        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
//        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        lessonDTO.setHomeworkIds(new HashSet<>());
//        lessonDTO.setStudentId(123L);
//        ResponseEntity<LessonDTO> actualCreateLessonResult = lessonController.createLesson(lessonDTO);
//        assertTrue(actualCreateLessonResult.hasBody());
//        assertEquals(3, actualCreateLessonResult.getHeaders().size());
//        assertEquals(HttpStatus.CREATED, actualCreateLessonResult.getStatusCode());
//        LessonDTO body = actualCreateLessonResult.getBody();
//        assertEquals("Jan 1, 2020 9:00am GMT+0100", body.getUpdatedBy());
//        assertEquals("01:01", body.getUpdatedAt().toLocalTime().toString());
//        assertEquals((short) 1, body.getMark().shortValue());
//        assertEquals((short) 1, body.getLessonNumber().shortValue());
//        assertEquals(123L, body.getId().longValue());
//        assertEquals("Jan 1, 2020 8:00am GMT+0100", body.getCreatedBy());
//        assertEquals("01:01", body.getCreatedAt().toLocalTime().toString());
//        assertEquals(123L, body.getCourseId().longValue());
//        assertEquals(123L, body.getStudentId().longValue());
//        assertTrue(body.getHomeworkIds().isEmpty());
//        verify(lessonRepository).save((Lesson) any());
//        verify(lessonDTO, atLeast(2)).getCourseId();
//        verify(lessonDTO, atLeast(1)).getId();
//        verify(lessonDTO, atLeast(2)).getStudentId();
//        verify(lessonDTO, atLeast(2)).getLessonNumber();
//        verify(lessonDTO).getMark();
//        verify(lessonDTO).getCreatedBy();
//        verify(lessonDTO).getUpdatedBy();
//        verify(lessonDTO).getCreatedAt();
//        verify(lessonDTO).getUpdatedAt();
//        verify(lessonDTO).setCreatedBy((String) any());
//        verify(lessonDTO).setCreatedAt((LocalDateTime) any());
//        verify(lessonDTO).setUpdatedBy((String) any());
//        verify(lessonDTO).setUpdatedAt((LocalDateTime) any());
//        verify(lessonDTO).setCourseId((Long) any());
//        verify(lessonDTO).setId((Long) any());
//        verify(lessonDTO).setLessonNumber((Short) any());
//        verify(lessonDTO).setMark((Short) any());
//        verify(lessonDTO).setHomeworkIds((Set<Long>) any());
//        verify(lessonDTO).setStudentId((Long) any());
//    }

    @Test
    void testUpdateLesson() throws URISyntaxException {
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
        CourseProperties CourseProperties = mock(CourseProperties.class);
        LessonController lessonController = new LessonController(
                new LessonServiceImpl(lessonRepository, lessonMapper, studentService, new CourseServiceImpl(courseRepository,
                        courseMapper, studentRepository1, instructorRepository), CourseProperties),
                mock(LessonRepository.class));

        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setCourseId(123L);
        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setId(123L);
        lessonDTO.setLessonNumber((short) 1);
        lessonDTO.setMark((short) 1);
        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setHomeworkIds(new HashSet<>());
        lessonDTO.setStudentId(123L);
        assertThrows(SystemException.class, () -> lessonController.updateLesson(1L, lessonDTO));
    }

    @Test
    void testDeleteLesson() throws Exception {
        doNothing().when(this.lessonService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/lessons/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteLesson2() throws Exception {
        doNothing().when(this.lessonService).delete((Long) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteLesson3() throws Exception {
        doNothing().when(this.lessonService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/lessons/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllLessons() throws Exception {
        when(this.lessonService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/lessons");
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllLessons2() throws Exception {
        when(this.lessonService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/lessons");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllLessonsForStudent() throws Exception {
        when(this.lessonService.getAllLessonsByStudentId((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/lessons/student/{studentId}", 123L);
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllLessonsForStudent2() throws Exception {
        when(this.lessonService.getAllLessonsByStudentId((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/lessons/student/{studentId}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetFinalMarkForLesson() throws Exception {
        CoursePassDTO coursePassDTO = new CoursePassDTO(10.0d, true, "You passed");
        when(this.lessonService.calculateFinalMark((StudentCourseDTO) any())).thenReturn(coursePassDTO);

        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setCourseId(123L);
        studentCourseDTO.setStudentId(123L);
        String content = (new ObjectMapper()).writeValueAsString(studentCourseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/lessons/final-mark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.finalMark").value(coursePassDTO.getFinalMark()))
                .andExpect(jsonPath("$.message").value(coursePassDTO.getMessage()))
                .andExpect(jsonPath("$.coursePassed").value(coursePassDTO.isCoursePassed()));
    }

    @Test
    void testGetLesson() throws Exception {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setCourseId(123L);
        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setId(123L);
        lessonDTO.setLessonNumber((short) 1);
        lessonDTO.setMark((short) 1);
        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setHomeworkIds(new HashSet<>());
        lessonDTO.setStudentId(123L);
        Optional<LessonDTO> ofResult = Optional.of(lessonDTO);
        when(this.lessonService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/lessons/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"lessonNumber\":1,\"mark\":1,\"studentId\":123,\"courseId"
                                        + "\":123,\"homeworkIds\":[]}"));
    }

    @Test
    void testGetLesson2() throws Exception {
        when(this.lessonService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/lessons/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetLesson3() throws Exception {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setCourseId(123L);
        lessonDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lessonDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setId(123L);
        lessonDTO.setLessonNumber((short) 1);
        lessonDTO.setMark((short) 1);
        lessonDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lessonDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lessonDTO.setHomeworkIds(new HashSet<>());
        lessonDTO.setStudentId(123L);
        Optional<LessonDTO> ofResult = Optional.of(lessonDTO);
        when(this.lessonService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/lessons/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.lessonController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"lessonNumber\":1,\"mark\":1,\"studentId\":123,\"courseId"
                                        + "\":123,\"homeworkIds\":[]}"));
    }
}

