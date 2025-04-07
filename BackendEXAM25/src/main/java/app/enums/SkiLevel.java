package app.enums;

import io.javalin.security.RouteRole;

public enum SkiLevel implements RouteRole {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}