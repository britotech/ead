package tech.brito.ead.course.domain.repositories;

import org.springframework.stereotype.Repository;
import tech.brito.ead.course.domain.models.Module;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ModuleRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        try {

            var jpql = "select m from Module m where m.course.id = :courseId and m.id = :moduleId";
            var query = manager.createQuery(jpql, Module.class);
            query.setParameter("courseId", courseId);
            query.setParameter("moduleId", moduleId);

            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
