package tech.brito.ead.course.domain.services;

import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.repositories.LessonRepository;
import tech.brito.ead.course.domain.repositories.ModuleRepository;
import tech.brito.ead.course.domain.models.Module;

import javax.transaction.Transactional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    public ModuleService(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public void delete(Module module) {
        var lessons = lessonRepository.findAllByModuleId(module.getId());
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }

        moduleRepository.delete(module);
    }
}
