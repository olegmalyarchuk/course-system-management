package com.coursesystem.e2e;

import com.coursesystem.controller.dto.RegistrationDTO;
import com.coursesystem.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class RegistrationE2ETest extends E2ETest {

    @Test
    void shouldRegisterNewUser() {
        final RegistrationDTO registrationDTO = TestUtil.getTestInstructorRegistrationDTO();

        given().header(CONTENT_TYPE, "application/json")
                .body(registrationDTO)
                .when()
                .post(testURL("/register"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("email", Matchers.is(registrationDTO.getEmail()))
                .body("firstName", Matchers.is(registrationDTO.getFirstName()))
                .body("secondName", Matchers.is(registrationDTO.getSecondName()));
    }

}
