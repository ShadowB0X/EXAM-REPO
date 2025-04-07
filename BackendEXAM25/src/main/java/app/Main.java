package app;

import app.config.ApplicationConfig;
import app.config.Populator;
import app.routes.SkiLessonRoutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int port = 7070;

        ApplicationConfig.getInstance()
                .initiateServer()
                .setRoute(SkiLessonRoutes.getRoutes()) // ✅ Pass connectionPool
                .handleException()
                .startServer(port);

        logger.info("🚀 Server started on http://localhost:" + port + "/api");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("🛑 Shutting down server...");
            ApplicationConfig.stopServer();

        }));

    }
}