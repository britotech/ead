package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.exceptions.ModuleNotFoundException;
import tech.brito.ead.course.domain.models.Module;
import tech.brito.ead.course.domain.repositories.LessonRepository;
import tech.brito.ead.course.domain.repositories.ModuleRepository;

import javax.transaction.Transactional;
import java.util.UUID;

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

    @Transactional
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public Module findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findByCourseIdAndId(courseId, moduleId).orElseThrow(() -> new ModuleNotFoundException());
    }

    public Page<Module> findAllByCourse(Specification<Module> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

    public Module findById(UUID moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() -> new ModuleNotFoundException());
    }
}
