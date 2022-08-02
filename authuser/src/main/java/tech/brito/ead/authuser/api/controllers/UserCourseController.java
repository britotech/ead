package tech.brito.ead.authuser.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.CourseDTO;
import tech.brito.ead.authuser.api.models.UserCourseDto;
import tech.brito.ead.authuser.domain.exceptions.EntityNotFoundException;
import tech.brito.ead.authuser.domain.services.UserCourseService;
import tech.brito.ead.authuser.domain.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {
    private final UserService userService;
    private final UserCourseService userCourseService;

    public UserCourseController(UserService userService, UserCourseService userCourseService) {
        this.userService = userService;
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{id}/courses")
    public Page<CourseDTO> getAllCoursesByUser(@PathVariable UUID id, @PageableDefault(sort = "name") Pageable pageable) {
        return userCourseService.getAllCoursesByUser(id, pageable);
    }

    @PostMapping("users/{id}/courses/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCourseDto saveSubscriptionUserInCourse(@PathVariable UUID id, @RequestBody @Valid UserCourseDto userCourseDto) {
        var user = userService.findById(id);
        var userCourse = userCourseService.saveSubscriptionUserInCourse(user, userCourseDto.getCourseId());

        userCourseDto.setId(userCourse.getId());
        userCourseDto.setUserId(id);
        return userCourseDto;
    }

    @DeleteMapping("users/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserCourseByCourse(@PathVariable UUID courseId) {

        if (!userCourseService.existsByCourseId(courseId)) {
            throw new EntityNotFoundException("UserCourse not found");
        }

        userCourseService.deleteAllUserCourseByCourse(courseId);
    }
}
