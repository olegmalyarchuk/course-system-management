package com.coursesystem.unit.service;

import com.coursesystem.dto.CourseDTO;
import com.coursesystem.dto.CourseReviewDTO;
import com.coursesystem.dto.StudentDTO;
import com.coursesystem.mapper.CourseReviewMapper;
import com.coursesystem.model.Course;
import com.coursesystem.model.CourseReview;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.service.CourseService;
import com.coursesystem.service.StudentService;
import com.coursesystem.service.impl.CourseReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseReviewServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CourseReviewServiceTest {
    @MockBean
    private CourseReviewMapper courseReviewMapper;

    @MockBean
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private CourseReviewServiceImpl courseReviewServiceImpl;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @Test
    void testSave() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        when(this.studentService.getStudentOrThrow((Long) any())).thenReturn(studentDTO);

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
        when(this.courseService.findByIdOrThrow((Long) any())).thenReturn(courseDTO);

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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user);

        CourseReview courseReview = new CourseReview();
        courseReview.setCourse(course);
        courseReview.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReview.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview.setReview("Review");
        courseReview.setId(123L);
        courseReview.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReview.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview.setStudent(student);
        when(this.courseReviewRepository.save((CourseReview) any())).thenReturn(courseReview);

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

        User user1 = new User();
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        user1.setUserRoles("User Roles");

        Student student1 = new Student();
        student1.setCourses(new HashSet<>());
        student1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setId(123L);
        student1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setUser(user1);

        CourseReview courseReview1 = new CourseReview();
        courseReview1.setCourse(course1);
        courseReview1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReview1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview1.setReview("Review");
        courseReview1.setId(123L);
        courseReview1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReview1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview1.setStudent(student1);

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        when(this.courseReviewMapper.toEntity((CourseReviewDTO) any())).thenReturn(courseReview1);
        when(this.courseReviewMapper.toDto((CourseReview) any())).thenReturn(courseReviewDTO);

        CourseReviewDTO courseReviewDTO1 = new CourseReviewDTO();
        courseReviewDTO1.setCourseId(123L);
        courseReviewDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setReview("Review");
        courseReviewDTO1.setId(123L);
        courseReviewDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO1.setStudentId(123L);
        assertSame(courseReviewDTO, this.courseReviewServiceImpl.save(courseReviewDTO1));
        verify(this.studentService).getStudentOrThrow((Long) any());
        verify(this.courseService).findByIdOrThrow((Long) any());
        verify(this.courseReviewRepository).save((CourseReview) any());
        verify(this.courseReviewMapper).toEntity((CourseReviewDTO) any());
        verify(this.courseReviewMapper).toDto((CourseReview) any());
    }

    @Test
    void testFindAll() {
        when(this.courseReviewRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.courseReviewServiceImpl.findAll().isEmpty());
        verify(this.courseReviewRepository).findAll();
    }

    @Test
    void testFindOne() {
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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user);

        CourseReview courseReview = new CourseReview();
        courseReview.setCourse(course);
        courseReview.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReview.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview.setReview("Review");
        courseReview.setId(123L);
        courseReview.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReview.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReview.setStudent(student);
        Optional<CourseReview> ofResult = Optional.of(courseReview);
        when(this.courseReviewRepository.findById((Long) any())).thenReturn(ofResult);

        CourseReviewDTO courseReviewDTO = new CourseReviewDTO();
        courseReviewDTO.setCourseId(123L);
        courseReviewDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        courseReviewDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setReview("Review");
        courseReviewDTO.setId(123L);
        courseReviewDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        courseReviewDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        courseReviewDTO.setStudentId(123L);
        when(this.courseReviewMapper.toDto((CourseReview) any())).thenReturn(courseReviewDTO);
        assertTrue(this.courseReviewServiceImpl.findOne(123L).isPresent());
        verify(this.courseReviewRepository).findById((Long) any());
        verify(this.courseReviewMapper).toDto((CourseReview) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.courseReviewRepository).deleteById((Long) any());
        this.courseReviewServiceImpl.delete(123L);
        verify(this.courseReviewRepository).deleteById((Long) any());
    }
}

