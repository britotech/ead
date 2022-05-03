package tech.brito.ead.course.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.api.models.CourseUserDto;
import tech.brito.ead.course.api.models.SubscriptionDto;
import tech.brito.ead.course.api.models.UserDto;
import tech.brito.ead.course.domain.services.CourseService;
import tech.brito.ead.course.domain.services.CourseUserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseService courseService;
    private final CourseUserService courseUserService;

    public CourseUserController(CourseService courseService, CourseUserService courseUserService) {
        this.courseService = courseService;
        this.courseUserService = courseUserService;
    }

    @GetMapping("/courses/{courseId}/users")
    public Page<UserDto> getAllUsersByCourse(@PathVariable UUID courseId, @PageableDefault(sort = "username") Pageable pageable) {
        return courseUserService.getAllUsersByCourse(courseId, pageable);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseUserDto saveSubscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody @Valid SubscriptionDto subscriptionDto) {
        var course = courseService.findById(courseId);
        return courseUserService.saveAndSendSubscriptionUserInCourse(course, subscriptionDto.getUserId());
    }
}
