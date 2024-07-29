package com.coursesystem.e2e;

import com.coursesystem.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class CourseE2ETest extends E2ETest {

    @Test
    void shouldCreateNewCourse() {
        var testStudent = testUtil.createTestStudent();
        var testInstructor = testUtil.createTestInstructor();
        var courseDTO = testUtil.createRequestCourseDTO(testStudent, testInstructor);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .body(courseDTO)
                .when()
                .post(testURL("/courses"))
                .then()
                .statusCode(201)
                .assertThat()
                .body("name", Matchers.is(TestUtil.DEFAULT_NAME))
                .body("numberOfLessons", equalTo(TestUtil.DEFAULT_NUMBER_OF_LESSONS.intValue()))
                .body("instructorIds", hasItems(testInstructor.getId().intValue()))
                .body("studentIds", hasItems(testStudent.getId().intValue()));
    }

    @Test
    void shouldAddNewStudentForTheCourse() {
        var testStudent = testUtil.createTestStudent();
        var course = testUtil.createCourse();

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .queryParam("studentIds", Collections.singleton(testStudent.getId()))
                .when()
                .post(testURL("/course/" + course.getId() + "/add-student"))
                .then()
                .statusCode(201)
                .assertThat()
                .body("name", Matchers.is(TestUtil.DEFAULT_NAME))
                .body("numberOfLessons", equalTo(TestUtil.DEFAULT_NUMBER_OF_LESSONS.intValue()))
                .body("instructorIds", hasItems(course.getInstructors().stream().findFirst().get().getId().intValue()))
                .body("studentIds", hasItems(testStudent.getId().intValue()));
    }

    @Test
    void shouldAddNewInstructorForTheCourse() {
        var course = testUtil.createCourseWithoutInstructor();
        var testInstructor = testUtil.createTestInstructor();

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .queryParam("instructorIds", Collections.singleton(testInstructor.getId()))
                .when()
                .post(testURL("/course/" + course.getId() + "/add-instructor"))
                .then()
                .statusCode(201)
                .assertThat()
                .body("name", Matchers.is(TestUtil.DEFAULT_NAME))
                .body("numberOfLessons", equalTo(TestUtil.DEFAULT_NUMBER_OF_LESSONS.intValue()))
                .body("instructorIds", hasItems(testInstructor.getId().intValue()))
                .body("studentIds", is(Collections.emptyList()));
    }

    @Test
    void shouldFetchAllStudentsForTheCourse() {
        var testStudent1 = testUtil.createTestStudent();
        var testStudent2 = testUtil.createTestStudent("second-test.student@test.com");
        var course = testUtil.createCourse();

        course.setStudents(Set.of(testStudent1, testStudent2));
        courseRepository.saveAndFlush(course);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .queryParam("courseIds", Collections.singleton(course.getId()))
                .when()
                .get(testURL("/courses/students"))
                .then()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body(course.getId().toString(), hasSize(2))
                .body(course.getId() + ".email", hasItems(is(testStudent1.getUser().getEmail()), is(testStudent2.getUser().getEmail())))
                .body(course.getId() + ".id", hasItems(is(testStudent1.getId().intValue()), is(testStudent2.getId().intValue())));
    }
}
