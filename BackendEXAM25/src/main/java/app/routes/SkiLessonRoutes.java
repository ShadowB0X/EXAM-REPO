package app.routes;

import app.controller.SkiLessonController;
import app.dao.SkiLessonDAOImp;
import app.enums.Role;
import app.enums.SkiLevel;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SkiLessonRoutes {

    public static EndpointGroup getRoutes() {
        SkiLessonDAOImp dao = new SkiLessonDAOImp();
        SkiLessonController controller = new SkiLessonController(dao);

        return () -> {
            path("/Ski", () -> {
                get("/", controller::getAll, Role.INSTRUCTOR);
                get("/{id}", controller::getById, Role.INSTRUCTOR);
                post("/", controller::create, Role.INSTRUCTOR);
                put("/{id}", controller::update, Role.ANYONE);
                delete("/{id}", controller::delete, Role.INSTRUCTOR);

                put("/{lessonId}/instructor/{instructorId}", controller::addInstructorToLesson, Role.INSTRUCTOR);
                get("/by-instructor/{instructorId}", controller::getLessonsByInstructor, Role.ANYONE);
                get("/level/{level}", controller::getByLevel, Role.INSTRUCTOR, SkiLevel.BEGINNER);
                get("/instructor-price-sum", controller::getPriceSumPerInstructor, Role.ANYONE);

                get("/instructions/{level}", controller.getInstructions);
                get("/instructions/{level}/total", controller.getTotalDuration, Role.ANYONE);
            });
        };
    }
}