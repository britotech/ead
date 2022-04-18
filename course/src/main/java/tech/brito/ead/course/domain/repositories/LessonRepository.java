package tech.brito.ead.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.course.domain.models.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findAllByModuleId(UUID moduleId);
}
