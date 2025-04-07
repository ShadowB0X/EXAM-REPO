package app.routes;

import app.config.ApplicationConfig;
import app.routes.SkiLessonRoutes;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SkiLessonRoutesTest {

    static ApplicationConfig app;

    @BeforeAll
    static void setup() {
        app = ApplicationConfig.getInstance()
                .initiateServer()
                .setRoute(SkiLessonRoutes.getRoutes())
                .handleException()
                .startServer(7070);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
        RestAssured.basePath = "/api";
    }

    @AfterAll
    static void teardown() {
        app.stopServer();
    }

    @Test
    void testGetAllSkiLessons() {
        given()
                .when()
                .get("/Ski")
                .then()
                .statusCode(anyOf(is(200), is(403), is(401)))
                .body("status", anyOf(equalTo("success"), nullValue()));
    }

    @Test
    void testGetInstructionsByLevel() {
        given()
                .when()
                .get("/Ski/instructions/beginner")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testGetInstructionsTotalDuration() {
        given()
                .when()
                .get("/Ski/instructions/beginner/total")
                .then()
                .statusCode(200)
                .body("level", equalTo("beginner"))
                .body("totalDurationMinutes", greaterThanOrEqualTo(1));
    }

    @Test
    void testGetByInvalidId() {
        given()
                .when()
                .get("/Ski/9999999")
                .then()
                .statusCode(anyOf(is(404), is(500)))
                .body("error", notNullValue());
    }
}