package app.dao;

import app.entities.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


import java.util.List;


@Transactional
public class InstructorDAO  {

    @PersistenceContext
    private EntityManager entityManager;

    public Instructor findById(Long id) {
        return entityManager.find(Instructor.class, id);
    }

    public List<Instructor> findAll() {
        return entityManager.createQuery("SELECT i FROM Instructor i", Instructor.class).getResultList();
    }

    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }

    public void delete(Long id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        if (instructor != null) {
            entityManager.remove(instructor);
        }
    }
}