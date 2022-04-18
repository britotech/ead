package tech.brito.ead.course.domain.services;

import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.repositories.LessonRepository;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
}
