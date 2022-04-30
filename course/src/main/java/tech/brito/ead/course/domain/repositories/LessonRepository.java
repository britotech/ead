package tech.brito.ead.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.brito.ead.course.domain.models.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID>, JpaSpecificationExecutor<Lesson> {

    List<Lesson> findAllByModuleId(UUID moduleId);

    Optional<Lesson> findByModuleIdAndId(UUID moduleId, UUID lessonId);
}
