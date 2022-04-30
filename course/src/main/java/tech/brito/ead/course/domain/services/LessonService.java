package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.exceptions.LessonNotFoundException;
import tech.brito.ead.course.domain.models.Lesson;
import tech.brito.ead.course.domain.repositories.LessonRepository;

import java.util.UUID;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findByModuleIdAndId(moduleId, lessonId).orElseThrow(() -> new LessonNotFoundException());
    }

    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public Page<Lesson> findAllByModule(Specification<Lesson> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }
}
