package app.entities;

import app.enums.SkiLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    private double latitude;
    private double longitude;

    private LocalDateTime starttime;
    private LocalDateTime endtime;

    @Enumerated(EnumType.STRING)
    private SkiLevel level;

    @ManyToOne
    private Instructor instructor;
}