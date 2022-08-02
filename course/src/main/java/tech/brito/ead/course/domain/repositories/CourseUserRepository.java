package tech.brito.ead.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.CourseUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    Optional<CourseUser> findByCourseAndUserId(Course course, UUID userId);

    List<CourseUser> findAllByCourseId(UUID courseId);

    boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);
}
