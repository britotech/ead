package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.course.domain.exceptions.UserNotFoundException;
import tech.brito.ead.course.domain.models.User;
import tech.brito.ead.course.domain.repositories.CourseRepository;
import tech.brito.ead.course.domain.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(UUID userId) {
        courseRepository.deleteCourseUserByUser(userId);
        userRepository.deleteById(userId);
    }

    public User findById(UUID userId) {
        return findOptionalById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Optional<User> findOptionalById(UUID userId) {
        return userRepository.findById(userId);
    }
}
