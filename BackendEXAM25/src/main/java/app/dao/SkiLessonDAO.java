package app.dao;

import app.entities.SkiLesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;


@Transactional
public class SkiLessonDAO {

    @PersistenceContext
    private EntityManager em;

    public SkiLesson findById(Long id) {
        return em.find(SkiLesson.class, id);
    }

    public List<SkiLesson> findAll() {
        return em.createQuery("SELECT s FROM SkiLesson s", SkiLesson.class).getResultList();
    }

    public void save(SkiLesson skiLesson) {
        em.persist(skiLesson);
    }

    public void update(SkiLesson skiLesson) {
        em.merge(skiLesson);
    }

    public void delete(Long id) {
        SkiLesson lesson = em.find(SkiLesson.class, id);
        if (lesson != null) {
            em.remove(lesson);
        }
    }
}