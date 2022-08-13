package tech.brito.ead.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.brito.ead.course.domain.models.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
    boolean existsByCourseAndUser(UUID courseId, UUID userId);
    void saveCourseUser(UUID courseId, UUID userId);
    void deleteCourseUserByCourse(UUID courseId);
    void deleteCourseUserByUser(UUID userId);
}
