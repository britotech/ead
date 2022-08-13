package tech.brito.ead.authuser.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.UserRegistrationDTO;
import tech.brito.ead.authuser.api.models.enums.UserStatus;
import tech.brito.ead.authuser.api.models.enums.UserType;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.services.UserService;

import javax.validation.Valid;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AuthenticationController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Valid UserRegistrationDTO userDto) {
        log.debug("registerUser -> {}", userDto);
        var user = modelMapper.map(userDto, User.class);
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.STUDENT);

        return userService.saveUser(user);
    }
}
