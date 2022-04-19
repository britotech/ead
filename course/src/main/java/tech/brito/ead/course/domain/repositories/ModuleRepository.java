package tech.brito.ead.course.domain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.course.domain.models.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

    List<Module> findAllByCourseId(UUID courseId);

    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);
}
