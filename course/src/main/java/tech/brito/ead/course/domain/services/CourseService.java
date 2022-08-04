package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.exceptions.CourseNotFoundException;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.repositories.CourseRepository;
import tech.brito.ead.course.domain.repositories.LessonRepository;
import tech.brito.ead.course.domain.repositories.ModuleRepository;
import tech.brito.ead.course.domain.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository,
                         ModuleRepository moduleRepository,
                         LessonRepository lessonRepository,
                         UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
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
        validateUserInstructor(course.getUserInstructor());
        return courseRepository.save(course);
    }

    private void validateUserInstructor(UUID userInstructor) {

     /*   try {
            var userDto = authUserClient.getUserById(userInstructor);
            if (userDto.getType().isStudent()) {
                throw new DomainRuleException("User must be INSTRUCTOR or ADMIN.");
            }
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DomainRuleException("Instructor not found.");
            }

            throw ex;
        }*/
    }

    public Course findById(UUID id) {
        return courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }
}
