package tech.brito.ead.authuser.api.controllers;

import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.InstructorDto;
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

    @PostMapping("/subscription")
    public User saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        var user = userService.findById(instructorDto.getUserId());
        user.setType(UserType.INSTRUCTOR);

        return userService.save(user);
    }
}
