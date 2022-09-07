package tech.brito.ead.authuser.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.clients.CourseClient;
import tech.brito.ead.authuser.api.models.CourseDTO;
import tech.brito.ead.authuser.domain.services.UserService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {
    private final UserService userService;
    private final CourseClient courseClient;

    public UserCourseController(UserService userService, CourseClient courseClient) {
        this.userService = userService;
        this.courseClient = courseClient;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/courses")
    public Page<CourseDTO> getAllCoursesByUser(@PathVariable UUID userId,
                                               @PageableDefault(sort = "name") Pageable pageable,
                                               @RequestHeader("Authorization") String token) {
        var user = userService.findById(userId);
        return courseClient.getAllCoursesByUser(user.getId(), pageable, token);
    }
}
