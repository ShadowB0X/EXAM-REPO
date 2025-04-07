package app.controller;

import app.dao.SkiLessonDAOImp;
import app.dtos.SkiLessonDTO;
import app.entities.SkiInstruction;
import app.enums.SkiLevel;
import app.exceptions.ApiException;
import app.services.SkiInstructionService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SkiLessonController {
    private final SkiInstructionService skiInstructionService = new SkiInstructionService();
    private final SkiLessonDAOImp dao;
    private static final Logger logger = LoggerFactory.getLogger(SkiLessonController.class);

    public SkiLessonController(SkiLessonDAOImp dao) {
        this.dao = dao;
    }

    public void addInstructorToLesson(Context ctx) {
        try {
            int lessonId = Integer.parseInt(ctx.pathParam("lessonId"));
            int instructorId = Integer.parseInt(ctx.pathParam("instructorId"));

            dao.addInstructorToSkiLesson(lessonId, instructorId);
            ctx.status(200).json(Map.of("status", "Instructor added to SkiLesson"));
        } catch (Exception e) {
            logger.error("Fejl ved tilføjelse af instructor til ski lesson", e);
            ctx.status(500).json(Map.of("error", "Kunne ikke tilføje instructor"));
        }
    }

    public void getLessonsByInstructor(Context ctx) {
        try {
            int instructorId = Integer.parseInt(ctx.pathParam("instructorId"));
            Set<SkiLessonDTO> lessons = dao.getSkiLessonsByInstructor(instructorId);
            ctx.json(Map.of("status", "success", "lessons", lessons));
        } catch (Exception e) {
            logger.error("Fejl ved hentning af ski lessons for instructor", e);
            ctx.status(500).json(Map.of("error", "Kunne ikke hente lessons for instructor"));
        }
    }

    public void getByLevel(Context ctx) {
        String levelParam = ctx.pathParam("level").toUpperCase();

        try {
            SkiLevel level = SkiLevel.valueOf(levelParam);
            List<SkiLessonDTO> allLessons = dao.getAll();

            List<SkiLessonDTO> filtered = allLessons.stream()
                    .filter(lesson -> lesson.getLevel() == level)
                    .toList();

            ctx.json(Map.of("status", "success", "filtered", filtered));
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid ski level: " + levelParam);
        }
    }

    public void getPriceSumPerInstructor(Context ctx) {
        try {
            List<SkiLessonDTO> allLessons = dao.getAll();

            Map<Long, Double> result = allLessons.stream()
                    .filter(l -> false)
                    .collect(Collectors.groupingBy(
                            SkiLessonDTO::getInstructorId,
                            Collectors.summingDouble(SkiLessonDTO::getPrice)
                    ));

            List<Map<String, Object>> response = result.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("instructorId", entry.getKey());
                        map.put("totalPrice", (Object) entry.getValue());
                        return map;
                    })
                    .toList();

            ctx.json(response);

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }



    public Handler getInstructions = ctx -> {
        String level = ctx.pathParam("level");
        try {
            var list = SkiInstructionService.getInstructionsByLevel(level);
            ctx.json(list);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Failed to fetch instructions"));
        }
    };
    public Handler getTotalDuration = ctx -> {
        String level = ctx.pathParam("level").toLowerCase();
        try {
            var list = SkiInstructionService.getInstructionsByLevel(level);

            if (list == null || list.isEmpty()) {
                ctx.status(404).json(Map.of("error", "No instructions found for level: " + level));
                return;
            }

            int total = list.stream().mapToInt(SkiInstruction::getDurationMinutes).sum();
            ctx.json(Map.of("level", level, "totalDurationMinutes", total));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Failed to calculate duration"));
        }
    };

    public void getAll(Context ctx) {
        try {
            List<SkiLessonDTO> lessons = dao.getAll();
            ctx.json(Map.of("status", "success", "data", lessons));
        } catch (Exception e) {
            logger.error("Fejl ved hentning af ski lessons", e);
            ctx.status(500).json(Map.of("error", "Kunne ikke hente ski lessons"));
        }
    }

    public void getById(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            SkiLessonDTO dto = dao.getById(id);
            if (dto == null) {
                ctx.status(404).json(Map.of("error", "Ski lesson ikke fundet"));
            } else {
                ctx.json(Map.of("status", "success", "data", dto));
            }
        } catch (Exception e) {
            logger.error("Fejl ved hentning af ski lesson med ID", e);
            ctx.status(500).json(Map.of("error", "Kunne ikke hente ski lesson"));
        }
    }

    public void create(Context ctx) {
        try {
            SkiLessonDTO dto = ctx.bodyAsClass(SkiLessonDTO.class);
            dao.create(dto);
            ctx.status(201).json(Map.of("status", "created", "lesson", dto.getName()));
        } catch (Exception e) {
            logger.error("Fejl ved oprettelse af ski lesson", e);
            ctx.status(400).json(Map.of("error", "Ugyldige data"));
        }
    }

    public void update(Context ctx) {
        try {
            SkiLessonDTO dto = ctx.bodyAsClass(SkiLessonDTO.class);
            dao.update(dto);
            ctx.status(200).json(Map.of("status", "updated", "id", dto.getId()));
        } catch (Exception e) {
            logger.error("Fejl ved opdatering af ski lesson", e);
            ctx.status(400).json(Map.of("error", "Ugyldige data ved opdatering"));
        }
    }

    public void delete(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            dao.delete(id);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Fejl ved sletning af ski lesson", e);
            ctx.status(500).json(Map.of("error", "Kunne ikke slette ski lesson"));
        }
    }
}