package tech.brito.ead.authuser.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.models.UserCourse;

import java.util.Optional;
import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    Optional<UserCourse> findByUserAndCourseId(User user, UUID courseId);
}
