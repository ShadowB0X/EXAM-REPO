package app.config;

import app.entities.Instructor;
import app.entities.SkiLesson;
import app.enums.SkiLevel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class Populator {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();


            Instructor anna = new Instructor(null, "Anna", "Frost", "anna@ski.dk", "12345678", 5, null);
            Instructor jonas = new Instructor(null, "Jonas", "Snow", "jonas@ski.dk", "87654321", 8, null);
            em.persist(anna);
            em.persist(jonas);


            SkiLesson lesson1 = new SkiLesson(null, "Beginner Basics", 300, 45.123, 12.345,
                    LocalDateTime.of(2025, 1, 10, 9, 0),
                    LocalDateTime.of(2025, 1, 10, 11, 0),
                    SkiLevel.BEGINNER, anna);

            SkiLesson lesson2 = new SkiLesson(null, "Advanced Turns", 450, 45.456, 12.678,
                    LocalDateTime.of(2025, 1, 11, 14, 0),
                    LocalDateTime.of(2025, 1, 11, 16, 0),
                    SkiLevel.ADVANCED, jonas);

            SkiLesson lesson3 = new SkiLesson(null, "Intermediate Flow", 400, 45.222, 12.999,
                    LocalDateTime.of(2025, 1, 12, 12, 0),
                    LocalDateTime.of(2025, 1, 12, 14, 0),
                    SkiLevel.INTERMEDIATE, anna);

            em.persist(lesson1);
            em.persist(lesson2);
            em.persist(lesson3);

            em.getTransaction().commit();

            System.out.println(" Populated database with ski lessons and instructors.");

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}