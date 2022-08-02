package tech.brito.ead.course.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import tech.brito.ead.course.api.clients.AuthUserClient;
import tech.brito.ead.course.api.models.CourseUserDto;
import tech.brito.ead.course.api.models.UserDto;
import tech.brito.ead.course.domain.exceptions.DomainRuleException;
import tech.brito.ead.course.domain.exceptions.SubscriptionAlreadyExistsException;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.CourseUser;
import tech.brito.ead.course.domain.repositories.CourseUserRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CourseUserService {
    private final CourseUserRepository courseUserRepository;

    private final AuthUserClient authUserClient;

    public CourseUserService(CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        return authUserClient.getAllUsersByCourse(courseId, pageable);
    }

    public boolean existsByUserId(UUID userId) {
        return courseUserRepository.existsByUserId(userId);
    }

    @Transactional
    public CourseUserDto saveAndSendSubscriptionUserInCourse(Course course, UUID userId) {
        validateSubscriptionUserInCourse(course, userId);
        validateActiveUser(userId);

        var courseUser = courseUserRepository.save(new CourseUser(course, userId));
        var courseUserDto = new CourseUserDto(courseUser.getId(), courseUser.getCourse().getId(), courseUser.getUserId());

        authUserClient.postSubscriptionUserInCourse(courseUserDto);
        return courseUserDto;
    }

    private void validateSubscriptionUserInCourse(Course course, UUID userId) {
        var optionalSubscritpionUserInCourse = courseUserRepository.findByCourseAndUserId(course, userId);

        if (optionalSubscritpionUserInCourse.isPresent()) {
            throw new SubscriptionAlreadyExistsException();
        }
    }

    private void validateActiveUser(UUID userId) {

        try {
            var userDto = authUserClient.getUserById(userId);
            if (userDto.getStatus().isBlocked()) {
                throw new DomainRuleException("User is blocked");
            }
        } catch (HttpStatusCodeException ex) {

            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DomainRuleException("User not found");
            }

            throw ex;
        }
    }

    @Transactional
    public void deleteAllCourseUserByUser(UUID userId) {
        courseUserRepository.deleteAllByUserId(userId);
    }
}
