package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import tech.brito.ead.course.api.clients.AuthUserClient;
import tech.brito.ead.course.domain.exceptions.CourseNotFoundException;
import tech.brito.ead.course.domain.exceptions.DomainRuleException;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.repositories.CourseRepository;
import tech.brito.ead.course.domain.repositories.CourseUserRepository;
import tech.brito.ead.course.domain.repositories.LessonRepository;
import tech.brito.ead.course.domain.repositories.ModuleRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final AuthUserClient authUserClient;
    private final CourseUserRepository courseUserRepository;

    public CourseService(CourseRepository courseRepository,
                         ModuleRepository moduleRepository,
                         LessonRepository lessonRepository,
                         AuthUserClient authUserClient,
                         CourseUserRepository courseUserRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.authUserClient = authUserClient;
        this.courseUserRepository = courseUserRepository;
    }

    public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Transactional
    public void delete(Course course) {
        deleteLinkedModules(course.getId());
        deleteLinkedCourseUsers(course.getId());
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

    private void deleteLinkedCourseUsers(UUID courseId) {
        var courseUsers = courseUserRepository.findAllByCourseId(courseId);
        if (!courseUsers.isEmpty()) {
            courseUserRepository.deleteAll(courseUsers);
        }
    }

    public Course save(Course course) {
        validateUserInstructor(course.getUserInstructor());
        return courseRepository.save(course);
    }

    private void validateUserInstructor(UUID userInstructor) {

        try {
            var userDto = authUserClient.getUserById(userInstructor);
            if (userDto.getType().isStudent()) {
                throw new DomainRuleException("User must be INSTRUCTOR or ADMIN.");
            }
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DomainRuleException("Instructor not found.");
            }

            throw ex;
        }
    }

    public Course findById(UUID id) {
        return courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }
}
