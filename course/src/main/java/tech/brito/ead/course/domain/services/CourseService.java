package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.core.specifications.SpecificationTemplate;
import tech.brito.ead.course.domain.exceptions.CourseNotFoundException;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.repositories.CourseRepository;
import tech.brito.ead.course.domain.repositories.LessonRepository;
import tech.brito.ead.course.domain.repositories.ModuleRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    public Page<Course> findAll(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }
    @Transactional
    public void delete(Course course) {
        deleteLinkedModules(course.getId());
        courseRepository.delete(course);
    }

    private void deleteLinkedModules(UUID courseId) {
        var modules = moduleRepository.findAllByCourseId(courseId);
        if (!modules.isEmpty()) {
            modules.forEach(m -> deleteLinkedLessons(m.getId()));
            moduleRepository.deleteAll(modules);
        }
    }

    private void deleteLinkedLessons(UUID moduleId) {
        var lessons = lessonRepository.findAllByModuleId(moduleId);
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course findById(UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }
}
