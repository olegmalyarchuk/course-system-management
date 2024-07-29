package com.coursesystem.util;

import com.coursesystem.controller.dto.LoginDTO;
import com.coursesystem.controller.dto.RegistrationDTO;
import com.coursesystem.dto.CourseDTO;
import com.coursesystem.dto.LessonDTO;
import com.coursesystem.model.Course;
import com.coursesystem.model.CourseReview;
import com.coursesystem.model.Instructor;
import com.coursesystem.model.Lesson;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.SecurityService;
import com.coursesystem.model.*;
import com.coursesystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class TestUtil {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private CourseRepository courseRepository;

    public static final String DEFAULT_FIRST_NAME = "Test";
    public static final String DEFAULT_SECOND_NAME = "Test";
    public static final String DEFAULT_EMAIL = "test.test@test.com";
    public static final UserRole DEFAULT_USER_ROLE = null;
    public static final String DEFAULT_PASSWORD = "0000";

    public static final String DEFAULT_SECOND_STUDENT_NAME = "Student";
    public static final String DEFAULT_STUDENT_EMAIL = "test.student@test.com";
    public static final String NO_ROLE_STUDENT_EMAIL = "no-role-test.student@test.com";
    public static final String DEFAULT_SECOND_INSTRUCTOR_NAME = "Instructor";
    public static final String DEFAULT_INSTRUCTOR_EMAIL = "test.instructor@test.com";
    public static final String NO_ROLE_INSTRUCTOR_EMAIL = "no-role-test.instructor@test.com";
    public static final String REGISTRATION_INSTRUCTOR_EMAIL = "reg.test.instructor@test.com";
    public static final String DEFAULT_SECOND_ADMIN_NAME = "Admin";
    public static final String DEFAULT_ADMIN_EMAIL = "test.admin@test.com";

    public static final Short DEFAULT_LESSON_NUMBER = 1;
    public static final Short DEFAULT_MARK = 2;

    public static final String DEFAULT_REVIEW = "DEFAULT_REVIEW";

    public static final String DEFAULT_NAME = "DEFAULT_NAME";
    public static final Short DEFAULT_NUMBER_OF_LESSONS = 10;

    public Student createTestStudentWithCourse(Set<Course> courses) {
        User user = createUser(Collections.singleton(UserRole.STUDENT), DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, DEFAULT_STUDENT_EMAIL);
        final Student student = new Student();
        student.setUser(user);
        student.setCourses(courses);
        studentRepository.save(student);
        return student;
    }

    public Student createTestStudent() {
        User user = createUser(Collections.singleton(UserRole.STUDENT), DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, DEFAULT_STUDENT_EMAIL);
        final Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
        return student;
    }

    public Student createTestStudent(final String email) {
        User user = createUser(Collections.singleton(UserRole.STUDENT), DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, email);
        final Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
        return student;
    }

    public Student createTestStudentWithCourse() {
        User user = createUser(Collections.singleton(UserRole.STUDENT), DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, DEFAULT_STUDENT_EMAIL);
        final Student student = new Student();
        student.setUser(user);
        student.setCourses(Collections.singleton(createCourse()));
        studentRepository.save(student);
        return student;
    }

    public Student createTestStudentEntity() {
        User user = createUser(Collections.singleton(UserRole.STUDENT), DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, DEFAULT_STUDENT_EMAIL);
        final Student student = new Student();
        student.setUser(user);
        return student;
    }

    public Student createTestStudentWithoutRole() {
        User user = createUser(DEFAULT_FIRST_NAME, DEFAULT_SECOND_STUDENT_NAME, NO_ROLE_STUDENT_EMAIL);
        final Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
        return student;
    }

    public Instructor createTestInstructor() {
        User user = createUser(Collections.singleton(UserRole.INSTRUCTOR), DEFAULT_FIRST_NAME, DEFAULT_SECOND_INSTRUCTOR_NAME, DEFAULT_INSTRUCTOR_EMAIL);
        final Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructorRepository.save(instructor);
        return instructor;
    }

    public Instructor createTestInstructorWithoutRole() {
        User user = createUser(Collections.singleton(UserRole.INSTRUCTOR), DEFAULT_FIRST_NAME, DEFAULT_SECOND_INSTRUCTOR_NAME, NO_ROLE_INSTRUCTOR_EMAIL);
        final Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructorRepository.save(instructor);
        return instructor;
    }

    public Instructor createTestInstructorEntity() {
        User user = createUser(Collections.singleton(UserRole.INSTRUCTOR), DEFAULT_FIRST_NAME, DEFAULT_SECOND_INSTRUCTOR_NAME, DEFAULT_INSTRUCTOR_EMAIL);
        final Instructor instructor = new Instructor();
        instructor.setUser(user);
        return instructor;
    }

    public static RegistrationDTO getTestInstructorRegistrationDTO() {
        return new RegistrationDTO(null, REGISTRATION_INSTRUCTOR_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_SECOND_INSTRUCTOR_NAME, Collections.singleton(UserRole.INSTRUCTOR), DEFAULT_PASSWORD);
    }

    public User createTestAdmin() {
        User admin = createUser(Collections.singleton(UserRole.ADMIN), DEFAULT_FIRST_NAME, DEFAULT_SECOND_ADMIN_NAME, DEFAULT_ADMIN_EMAIL);
        return admin;
    }

    public User createTestUser() {
        User admin = createUser(Collections.singleton(DEFAULT_USER_ROLE), DEFAULT_FIRST_NAME, DEFAULT_SECOND_NAME, DEFAULT_EMAIL);
        return admin;
    }

    public User createUser(Set<UserRole> userRoles, String firstName, String secondName, String email) {
        final User user = new User();
        user.setUserRoles(JsonUtil.serialize(userRoles));
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setEmail(email);
        user.setPassword(encode(DEFAULT_PASSWORD));
        userRepository.save(user);

        return user;
    }

    public User createUser(String firstName, String secondName, String email) {
        final User user = new User();
        user.setUserRoles(JsonUtil.serialize(UserRole.NO_ROLE));
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setEmail(email);
        user.setPassword(encode(DEFAULT_PASSWORD));
        userRepository.save(user);

        return user;
    }

    public User createUserEntity() {
        final User user = new User();
        user.setUserRoles(JsonUtil.serialize(Collections.singletonList(DEFAULT_USER_ROLE)));
        user.setFirstName(DEFAULT_FIRST_NAME);
        user.setSecondName(DEFAULT_SECOND_NAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setPassword(encode(DEFAULT_PASSWORD));
        return user;
    }

    private String encode(String input) {
        return passwordEncoder.encode(input);
    }

    public Lesson createLesson() {
        final Student student = createTestStudentWithCourse();
        Lesson lesson = Lesson.builder()
                .lessonNumber(DEFAULT_LESSON_NUMBER)
                .mark(DEFAULT_MARK)
                .course(student.getCourses().stream().findAny().get())
                .student(student)
                .build();
        final Lesson savedLesson = lessonRepository.save(lesson);
        return savedLesson;
    }

    public LessonDTO createLessonDTO(final Long studentId, final Long courseId) {
        final LessonDTO lesson = new LessonDTO();
        lesson.setLessonNumber(DEFAULT_LESSON_NUMBER);
        lesson.setMark(DEFAULT_LESSON_NUMBER);
        lesson.setCourseId(courseId);
        lesson.setStudentId(studentId);
        return lesson;
    }

    public Lesson createLesson(final Student student, final Course course, final Integer finalMark, final Integer lessonNumber) {
        Lesson lesson = Lesson.builder()
                .lessonNumber(lessonNumber.shortValue())
                .mark(finalMark.shortValue())
                .course(course)
                .student(student)
                .build();
        final Lesson savedLesson = lessonRepository.save(lesson);
        return savedLesson;
    }

    public Course createCourse() {
        Course course = Course.builder()
                .name(DEFAULT_NAME)
                .numberOfLessons(DEFAULT_NUMBER_OF_LESSONS)
                .instructors(Collections.singleton(createTestInstructor()))
                .build();
        final Course save = courseRepository.save(course);
        return save;
    }

    public Course createCourseWithInstructorAndStudent() {
        Course course = Course.builder()
                .name(DEFAULT_NAME)
                .numberOfLessons(DEFAULT_NUMBER_OF_LESSONS)
                .instructors(Collections.singleton(createTestInstructor()))
                .students(Collections.singleton(createTestStudent()))
                .build();
        final Course save = courseRepository.save(course);
        return save;
    }

    public Course createCourseWithoutInstructor() {
        Course course = Course.builder()
                .name(DEFAULT_NAME)
                .numberOfLessons(DEFAULT_NUMBER_OF_LESSONS)
                .build();
        final Course save = courseRepository.save(course);
        return save;
    }

    public CourseDTO createRequestCourseDTO(final Student student, final Instructor instructor) {
        final CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(DEFAULT_NAME);
        courseDTO.setNumberOfLessons(DEFAULT_NUMBER_OF_LESSONS);
        courseDTO.setStudentIds(Collections.singleton(student.getId()));
        courseDTO.setInstructorIds(Collections.singleton(instructor.getId()));
        return courseDTO;
    }

    public CourseReview createCourseReviewEntity() {
        final Course course = createCourse();
        CourseReview courseReview = CourseReview.builder()
                .review(DEFAULT_REVIEW)
                .course(course)
                .student(createTestStudentWithCourse(Collections.singleton(course)))
                .build();

        return courseReview;
    }

    public String generateToken(final String email, final String password ) {
        final LoginDTO loginDTO = new LoginDTO(email, password);
        final HttpHeaders httpHeaders = new HttpHeaders();
        final String token = securityService.login(loginDTO, httpHeaders);
        return token;
    }
}
