package tech.brito.ead.authuser.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.InstructorDTO;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.services.UserService;
import tech.brito.ead.authuser.enums.UserType;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    private final UserService userService;

    public InstructorController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public User saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {
        var user = userService.findById(instructorDTO.getUserId());
        return userService.subscriptionInstructor(user);
    }
}
