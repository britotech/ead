package tech.brito.ead.authuser.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.UserRegistrationDTO;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.services.UserService;
import tech.brito.ead.authuser.enums.UserStatus;
import tech.brito.ead.authuser.enums.UserType;

import javax.validation.Valid;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Valid UserRegistrationDTO userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.debug("registerUser -> {}", userDto);
        var user = modelMapper.map(userDto, User.class);
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.STUDENT);

        return userService.saveUser(user);
    }
}
