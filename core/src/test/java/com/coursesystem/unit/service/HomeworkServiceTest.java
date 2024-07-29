package com.coursesystem.unit.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.StringInputStream;
import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.HomeworkDTO;
import com.coursesystem.mapper.HomeworkMapper;
import com.coursesystem.mapper.LessonMapper;
import com.coursesystem.model.Course;
import com.coursesystem.model.Homework;
import com.coursesystem.model.Lesson;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.repository.HomeworkRepository;
import com.coursesystem.service.LessonService;
import com.coursesystem.service.impl.HomeworkServiceImpl;
import com.coursesystem.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {HomeworkServiceImpl.class})
@ExtendWith(SpringExtension.class)
class HomeworkServiceTest {

    @MockBean
    private AmazonS3 amazonS3;

    @MockBean
    private LessonMapper lessonMapper;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private HomeworkMapper homeworkMapper;

    @MockBean
    private HomeworkRepository homeworkRepository;

    @Autowired
    private HomeworkServiceImpl homeworkServiceImpl;

    @Test
    void testDownloadFile() throws SdkClientException, UnsupportedEncodingException {
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
        user.setCreatedAt(null);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(null);
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

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lesson.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setId(123L);
        lesson.setLessonNumber((short) 1);
        lesson.setMark((short) 1);
        lesson.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lesson.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setStudent(student);
        lesson.setHomeworks(new HashSet<>());

        Homework homework = new Homework();
        homework.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homework.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setId(123L);
        homework.setLesson(lesson);
        homework.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homework.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setOriginalFileName("foo.txt");
        homework.setStoredFileName("foo.txt");
        Optional<Homework> ofResult = Optional.of(homework);
        when(this.homeworkRepository.findByLessonId((Long) any())).thenReturn(ofResult);

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        when(this.homeworkMapper.toDto((Homework) any())).thenReturn(homeworkDTO);

        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new StringInputStream("Lorem ipsum dolor sit amet."));
        when(this.amazonS3.getObject((String) any(), (String) any())).thenReturn(s3Object);

        HomeworkDTO homeworkDTO1 = new HomeworkDTO();
        homeworkDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO1.setId(123L);
        homeworkDTO1.setLessonId(123L);
        homeworkDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO1.setOriginalFileName("foo.txt");
        homeworkDTO1.setStoredFileName("foo.txt");
        assertEquals(27, this.homeworkServiceImpl.downloadFile(homeworkDTO1).length);
        verify(this.homeworkRepository).findByLessonId((Long) any());
        verify(this.homeworkMapper).toDto((Homework) any());
        verify(this.amazonS3).getObject((String) any(), (String) any());
    }

    @Test
    void testDownloadFile2() throws SdkClientException {
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
        user.setCreatedAt(null);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(null);
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

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lesson.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setId(123L);
        lesson.setLessonNumber((short) 1);
        lesson.setMark((short) 1);
        lesson.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lesson.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setStudent(student);
        lesson.setHomeworks(new HashSet<>());

        Homework homework = new Homework();
        homework.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homework.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setId(123L);
        homework.setLesson(lesson);
        homework.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homework.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setOriginalFileName("foo.txt");
        homework.setStoredFileName("foo.txt");
        Optional<Homework> ofResult = Optional.of(homework);
        when(this.homeworkRepository.findByLessonId((Long) any())).thenReturn(ofResult);

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        when(this.homeworkMapper.toDto((Homework) any())).thenReturn(homeworkDTO);
        when(this.amazonS3.getObject((String) any(), (String) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));

        HomeworkDTO homeworkDTO1 = new HomeworkDTO();
        homeworkDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO1.setId(123L);
        homeworkDTO1.setLessonId(123L);
        homeworkDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO1.setOriginalFileName("foo.txt");
        homeworkDTO1.setStoredFileName("foo.txt");
        assertThrows(SystemException.class, () -> this.homeworkServiceImpl.downloadFile(homeworkDTO1));
        verify(this.homeworkRepository).findByLessonId((Long) any());
        verify(this.homeworkMapper).toDto((Homework) any());
        verify(this.amazonS3).getObject((String) any(), (String) any());
    }

    @Test
    void testDeleteFile() throws SdkClientException {
        doNothing().when(this.amazonS3).deleteObject((String) any(), (String) any());

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        assertEquals("foo.txt removed ...", this.homeworkServiceImpl.deleteFile(homeworkDTO));
        verify(this.amazonS3).deleteObject((String) any(), (String) any());
    }

    @Test
    void testDeleteFile2() throws SdkClientException {
        doThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR)).when(this.amazonS3)
                .deleteObject((String) any(), (String) any());

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        assertThrows(SystemException.class, () -> this.homeworkServiceImpl.deleteFile(homeworkDTO));
        verify(this.amazonS3).deleteObject((String) any(), (String) any());
    }

    @Test
    void testFindByLessonIdOrThrow() throws SystemException {
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
        user.setCreatedAt(null);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(null);
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

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        lesson.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setId(123L);
        lesson.setLessonNumber((short) 1);
        lesson.setMark((short) 1);
        lesson.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        lesson.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        lesson.setStudent(student);
        lesson.setHomeworks(new HashSet<>());

        Homework homework = new Homework();
        homework.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homework.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setId(123L);
        homework.setLesson(lesson);
        homework.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homework.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homework.setOriginalFileName("foo.txt");
        homework.setStoredFileName("foo.txt");
        Optional<Homework> ofResult = Optional.of(homework);
        when(this.homeworkRepository.findByLessonId((Long) any())).thenReturn(ofResult);

        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        homeworkDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setId(123L);
        homeworkDTO.setLessonId(123L);
        homeworkDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        homeworkDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        homeworkDTO.setOriginalFileName("foo.txt");
        homeworkDTO.setStoredFileName("foo.txt");
        when(this.homeworkMapper.toDto((Homework) any())).thenReturn(homeworkDTO);
        assertSame(homeworkDTO, this.homeworkServiceImpl.findByLessonIdOrThrow(123L));
        verify(this.homeworkRepository).findByLessonId((Long) any());
        verify(this.homeworkMapper).toDto((Homework) any());
    }
}

