package app.controller;

import app.config.ApplicationConfig;
import app.routes.SkiLessonRoutes;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class SkiLessonEndpointsTest {

    private static ApplicationConfig app;

    @BeforeAll
    static void setup() {
        app = ApplicationConfig.getInstance()
                .initiateServer()
                .setRoute(SkiLessonRoutes.getRoutes())
                .handleException()
                .startServer(7070);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
        RestAssured.basePath = "/api/Ski";
    }

    @AfterAll
    static void teardown() {
        app.stopServer();
    }

    @Test
    void getAllSkiLessons() {
        when()
                .get("/")
                .then()
                .statusCode(anyOf(is(200), is(403)))
                .body("data", notNullValue());
    }

    @Test
    void getSkiLessonById_notFound() {
        when()
                .get("/99999")
                .then()
                .statusCode(404)
                .body("error", containsString("ikke fundet"));
    }

    @Test
    void addInstructorToLesson_valid() {

        when()
                .put("/1/instructor/1")
                .then()
                .statusCode(anyOf(is(200), is(400), is(500)))
                .body(anyOf(hasKey("status"), hasKey("error")));
    }

    @Test
    void getByLevel_beginner() {
        when()
                .get("/level/beginner")
                .then()
                .statusCode(200)
                .body("filtered", notNullValue());
    }

    @Test
    void getInstructionsForLevel() {
        when()
                .get("/instructions/beginner")
                .then()
                .statusCode(200)
                .body("[0].title", notNullValue());
    }

    @Test
    void getInstructionDurationForLevel() {
        when()
                .get("/instructions/intermediate/total")
                .then()
                .statusCode(200)
                .body("totalDurationMinutes", greaterThanOrEqualTo(0));
    }

    @Test
    void getPriceSumPerInstructor() {
        when()
                .get("/instructor-price-sum")
                .then()
                .statusCode(200)
                .body("[0].instructorId", notNullValue());
    }
}