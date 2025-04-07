package app.dao;

import app.config.HibernateConfig;
import app.dtos.InstructorDTO;
import app.entities.Instructor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class InstructorDAOImp implements IDAO<InstructorDTO> {

    protected EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    @Override
    public InstructorDTO getById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Instructor i = em.find(Instructor.class, id);
            return i != null ? new InstructorDTO(i) : null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<InstructorDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Instructor i", Instructor.class)
                    .getResultList()
                    .stream()
                    .map(InstructorDTO::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public void create(InstructorDTO dto) {
        throw new UnsupportedOperationException("Not implemented for this exercise.");
    }

    @Override
    public void update(InstructorDTO dto) {
        throw new UnsupportedOperationException("Not implemented for this exercise.");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented for this exercise.");
    }
}