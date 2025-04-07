package app.dtos;

import app.entities.SkiLesson;
import app.enums.SkiLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SkiLessonDTO {

    private Long id;
    private String name;
    private double price;
    private double latitude;
    private double longitude;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private SkiLevel level;
    private Long instructorId;


    public SkiLessonDTO(SkiLesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.price = lesson.getPrice();
        this.latitude = lesson.getLatitude();
        this.longitude = lesson.getLongitude();
        this.starttime = lesson.getStarttime();
        this.endtime = lesson.getEndtime();
        this.level = lesson.getLevel();
        if (lesson.getInstructor() != null)
            this.instructorId = lesson.getInstructor().getId();
    }

    public SkiLessonDTO(String name, double price, double latitude, double longitude,
                        LocalDateTime starttime, LocalDateTime endtime, SkiLevel level, Long instructorId) {
        this.name = name;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.starttime = starttime;
        this.endtime = endtime;
        this.level = level;
        this.instructorId = instructorId;
    }


    public static List<SkiLessonDTO> toDTOList(List<SkiLesson> lessons) {
        return lessons.stream().map(SkiLessonDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkiLessonDTO dto)) return false;
        return getId().equals(dto.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}