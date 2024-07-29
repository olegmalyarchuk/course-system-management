package com.coursesystem.e2e;

import com.coursesystem.Application;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.repository.HomeworkRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.SecurityService;
import com.coursesystem.util.TestUtil;
import com.coursesystem.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class, TestUtil.class})
abstract public class E2ETest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected CourseReviewRepository courseReviewRepository;
    @Autowired
    protected CourseRepository courseRepository;
    @Autowired
    protected InstructorRepository instructorRepository;
    @Autowired
    protected LessonRepository lessonRepository;
    @Autowired
    protected HomeworkRepository homeworkRepository;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TestUtil testUtil;
    @Autowired
    protected SecurityService securityService;

    protected String testURL(String path) {
        return "http://localhost:" + port + "/api/v1" + path;
    }

    @BeforeEach
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void setUp() {
        deleteWholeDB();
    }

    @AfterEach
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void clearDb() {
        deleteWholeDB();
    }

    private void deleteWholeDB() {

        homeworkRepository.deleteAll();
        courseReviewRepository.deleteAll();
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
        instructorRepository.deleteAll();
        studentRepository.deleteAll();
        userRepository.deleteAll();

        testUtil.createTestAdmin();
    }
}
