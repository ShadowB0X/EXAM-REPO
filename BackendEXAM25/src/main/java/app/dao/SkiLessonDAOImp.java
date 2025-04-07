package app.dao;

import app.config.HibernateConfig;
import app.dao.IDAO;
import app.dtos.SkiLessonDTO;
import app.entities.Instructor;
import app.entities.SkiLesson;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SkiLessonDAOImp implements IDAO<SkiLessonDTO>, ISkiLessonInstructorDAO {

    protected EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    @Override
    public SkiLessonDTO getById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            SkiLesson s = em.find(SkiLesson.class, id);
            return s != null ? new SkiLessonDTO(s) : null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<SkiLessonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SkiLesson s", SkiLesson.class)
                    .getResultList()
                    .stream()
                    .map(SkiLessonDTO::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public void create(SkiLessonDTO dto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SkiLesson entity = dtoToEntity(dto, em);
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(SkiLessonDTO dto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SkiLesson updated = dtoToEntity(dto, em);
            em.merge(updated);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SkiLesson s = em.find(SkiLesson.class, id);
            if (s != null) em.remove(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private SkiLesson dtoToEntity(SkiLessonDTO dto, EntityManager em) {
        SkiLesson s = new SkiLesson();
        s.setId(dto.getId());
        s.setName(dto.getName());
        s.setPrice(dto.getPrice());
        s.setLatitude(dto.getLatitude());
        s.setLongitude(dto.getLongitude());
        s.setStarttime(dto.getStarttime());
        s.setEndtime(dto.getEndtime());
        s.setLevel(dto.getLevel());
        s.setInstructor(em.find(Instructor.class, dto.getInstructorId()));
        return s;
    }

    @Override
    public void addInstructorToSkiLesson(int lessonId, int instructorId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SkiLesson lesson = em.find(SkiLesson.class, (long) lessonId);
            Instructor instructor = em.find(Instructor.class, (long) instructorId);
            if (lesson != null && instructor != null) {
                lesson.setInstructor(instructor);
                em.merge(lesson);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Set<SkiLessonDTO> getSkiLessonsByInstructor(int instructorId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<SkiLesson> result = em.createQuery(
                            "SELECT s FROM SkiLesson s WHERE s.instructor.id = :id", SkiLesson.class)
                    .setParameter("id", (long) instructorId)
                    .getResultList();

            return result.stream().map(SkiLessonDTO::new).collect(Collectors.toSet());
        } finally {
            em.close();
        }
    }
}