package com.coursesystem.e2e;

import com.coursesystem.model.Student;
import com.coursesystem.model.UserRole;
import com.coursesystem.util.TestUtil;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class UserE2ETest extends E2ETest {

    @Test
    void shouldFetchAllUsers() {
        testUtil.createTestStudent();
        testUtil.createTestInstructor();

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .when()
                .get(testURL("/users"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("", hasSize(3));
    }
    @Test
    void shouldNotBeAvailableAllUsersForInstructor() {
        testUtil.createTestStudent();
        testUtil.createTestInstructor();

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_INSTRUCTOR_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .when()
                .get(testURL("/users"))
                .then()
                .statusCode(403);
    }

//    @Test
//    void shouldAssignRole() {
//        final Student testStudentWithoutRole = testUtil.createTestStudentWithoutRole();
//
//        given().header(CONTENT_TYPE, "application/json")
//                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_ADMIN_EMAIL, TestUtil.DEFAULT_PASSWORD))
//                .body(UserRole.STUDENT.name())
//                .when()
//                .put(testURL("/users/" + testStudentWithoutRole.getUser().getId() + "/role"))
//                .then()
//                .statusCode(200);
//    }

    @Test
    void shouldNotBeAvailableAssignRoleInstructor() {
        final Student testStudentWithoutRole = testUtil.createTestStudentWithoutRole();
        testUtil.createTestInstructor();

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, testUtil.generateToken(TestUtil.DEFAULT_INSTRUCTOR_EMAIL, TestUtil.DEFAULT_PASSWORD))
                .body(UserRole.STUDENT.name())
                .when()
                .put(testURL("/users/" + testStudentWithoutRole.getUser().getId() + "/role"))
                .then()
                .statusCode(403);
    }
}
