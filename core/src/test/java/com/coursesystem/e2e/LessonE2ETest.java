package com.coursesystem.e2e;


import com.coursesystem.dto.StudentCourseDTO;
import com.coursesystem.util.TestUtil;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class LessonE2ETest extends E2ETest {

    @Test
    void shouldCreateNewLesson() {
        var course = testUtil.createCourseWithInstructorAndStudent();
        var student = course.getStudents().stream().findFirst().get();
        var lessonDTO = testUtil.createLessonDTO(student.getId(), course.getId());

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .body(lessonDTO)
                .when()
                .post(testURL("/lessons"))
                .then()
                .statusCode(201)
                .assertThat()
                .body("lessonNumber", is(lessonDTO.getLessonNumber().intValue()))
                .body("mark", is(lessonDTO.getMark().intValue()))
                .body("studentId", is(student.getId().intValue()))
                .body("courseId", is(course.getId().intValue()));
    }

    @Test
    void shouldPassTheCourse() {
        var testStudent = testUtil.createTestStudent();
        var course = testUtil.createCourse();
        var studentCourseDTO = new StudentCourseDTO(testStudent.getId(), course.getId());
        for (int i = 1; i <= 10; i++) {
            testUtil.createLesson(testStudent, course, 10, i);
        }

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .body(studentCourseDTO)
                .when()
                .post(testURL("/lessons/final-mark"))
                .then()
                .statusCode(200)
                .log().body()
                .assertThat()
                .body("coursePassed", equalTo(true))
                .body("message", equalTo("You passed course, congratulations."));
    }

    @Test
    void shouldFailTheCourse() {
        var testStudent = testUtil.createTestStudent();
        var course = testUtil.createCourse();
        var studentCourseDTO = new StudentCourseDTO(testStudent.getId(), course.getId());
        for (int i = 1; i <= 10; i++) {
            testUtil.createLesson(testStudent, course, 7, i);
        }

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .body(studentCourseDTO)
                .when()
                .post(testURL("/lessons/final-mark"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("coursePassed", equalTo(false))
                .body("message", equalTo("You didn't pass course, average mark should be above 8."));
    }
}
