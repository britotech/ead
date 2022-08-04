package tech.brito.ead.authuser.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.ImageUpdateDTO;
import tech.brito.ead.authuser.api.models.PasswordUpdateDTO;
import tech.brito.ead.authuser.api.models.UserUpdateDTO;
import tech.brito.ead.authuser.core.specifications.SpecificationTemplate;
import tech.brito.ead.authuser.domain.exceptions.DomainRuleException;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Page<User> getAllUsers(SpecificationTemplate.UserSpec spec, @PageableDefault(sort = "username") Pageable pageable) {
        Page<User> userPage = userService.findAll(spec, pageable);
        userPage.toList().forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        });

        return userPage;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable UUID userId, @RequestBody @Valid UserUpdateDTO userDto) {
        var userSaved = userService.findById(userId);
        modelMapper.map(userDto, userSaved);
        return userService.save(userSaved);
    }

    @PutMapping("/{userId}/password")
    public String updatePassword(@PathVariable UUID userId, @RequestBody @Valid PasswordUpdateDTO passwordDTO) {
        var userSaved = userService.findById(userId);
        if (!userSaved.getPassword().equals(passwordDTO.getOldPassword())) {
            throw new DomainRuleException("Old password does not match user's current password");
        }

        userSaved.setPassword(passwordDTO.getPassword());
        userService.save(userSaved);
        return "Password updated sucessfully";
    }

    @PutMapping("/{userId}/image")
    public String updateImage(@PathVariable UUID userId, @RequestBody @Valid ImageUpdateDTO imageDTO) {
        var userSaved = userService.findById(userId);
        userSaved.setImageUrl(imageDTO.getImageUrl());
        userService.save(userSaved);
        return "Image updated sucessfully";
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        var user = userService.findById(userId);
        userService.delete(user);
    }
}
