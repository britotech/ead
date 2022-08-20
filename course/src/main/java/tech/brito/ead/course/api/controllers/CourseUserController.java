package tech.brito.ead.course.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.core.specifications.SpecificationTemplate;
import tech.brito.ead.course.domain.exceptions.DomainRuleException;
import tech.brito.ead.course.domain.models.SubscriptionDto;
import tech.brito.ead.course.domain.models.User;
import tech.brito.ead.course.domain.services.CourseService;
import tech.brito.ead.course.domain.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseService courseService;
    private final UserService userService;

    public CourseUserController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses/{courseId}/users")
    public Page<User> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                          @PathVariable UUID courseId,
                                          @PageableDefault(sort = "username") Pageable pageable) {
        var course = courseService.findById(courseId);
        return userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveSubscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody @Valid SubscriptionDto subscriptionDto) {
        var course = courseService.findById(courseId);
        var user = userService.findOptionalById(subscriptionDto.getUserId());
        if (!user.isPresent()) {
            throw new DomainRuleException("User not found");
        }

        courseService.saveSubscriptionUserInCourseAndSendNotification(course, user.get());
        return "Subscription created sucessfully";
    }
}
