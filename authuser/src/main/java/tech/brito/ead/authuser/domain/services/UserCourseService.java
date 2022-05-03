package tech.brito.ead.authuser.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.brito.ead.authuser.api.clients.CourseClient;
import tech.brito.ead.authuser.api.models.CourseDTO;
import tech.brito.ead.authuser.domain.exceptions.SubscriptionAlreadyExistsException;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.models.UserCourse;
import tech.brito.ead.authuser.domain.repositories.UserCourseRepository;

import java.util.UUID;

@Service
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;

    private final CourseClient courseClient;

    public UserCourseService(UserCourseRepository userCourseRepository, CourseClient courseClient) {
        this.userCourseRepository = userCourseRepository;
        this.courseClient = courseClient;
    }

    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {
        return courseClient.getAllCoursesByUser(userId, pageable);
    }

    public UserCourse saveSubscriptionUserInCourse(User user, UUID courseId){
        validateSubscriptionUserInCourse(user, courseId);
        var userCourse = new UserCourse(user, courseId);
        return userCourseRepository.save(userCourse);
    }

    private void validateSubscriptionUserInCourse(User user, UUID courseId) {
        var optionalUserCourse = userCourseRepository.findByUserAndCourseId(user, courseId);

        if (optionalUserCourse.isPresent()) {
            throw new SubscriptionAlreadyExistsException();
        }
    }
}
