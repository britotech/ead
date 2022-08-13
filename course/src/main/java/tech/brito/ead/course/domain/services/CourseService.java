package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.exceptions.CourseNotFoundException;
import tech.brito.ead.course.domain.exceptions.DomainRuleException;
import tech.brito.ead.course.domain.exceptions.SubscriptionAlreadyExistsException;
import tech.brito.ead.course.domain.exceptions.UserBlockedException;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.User;
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
    private final UserService userService;

    public CourseService(CourseRepository courseRepository,
                         ModuleRepository moduleRepository,
                         LessonRepository lessonRepository,
                         UserService userService) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.userService = userService;
    }

    public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Transactional
    public void delete(Course course) {
        deleteLinkedModules(course.getId());
        courseRepository.deleteCourseUserByCourse(course.getId());
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

    private void validateUserInstructor(UUID userId) {

        var user = userService.findOptionalById(userId);
        if (!user.isPresent()) {
            throw new DomainRuleException("UserInstructor not found");
        }

        if (user.get().getType().isStudent()) {
            throw new DomainRuleException("User must be INSTRUCTOR or ADMIN.");
        }
    }

    public Course findById(UUID id) {
        return courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }

    @Transactional
    public void saveSubscriptionUserInCourse(Course course, User user) {
        if (courseRepository.existsByCourseAndUser(course.getId(), user.getId())) {
            throw new SubscriptionAlreadyExistsException();
        }

        if (user.getStatus().isBlocked()) {
            throw new UserBlockedException();
        }

        courseRepository.saveCourseUser(course.getId(), user.getId());
    }
}
