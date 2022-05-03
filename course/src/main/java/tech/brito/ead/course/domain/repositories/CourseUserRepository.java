package tech.brito.ead.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.CourseUser;

import java.util.Optional;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    Optional<CourseUser> findByCourseAndUserId(Course course, UUID userId);
}
