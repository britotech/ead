package tech.brito.ead.authuser.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.authuser.api.models.ImageUpdateDTO;
import tech.brito.ead.authuser.api.models.PasswordUpdateDTO;
import tech.brito.ead.authuser.api.models.UserUpdateDTO;
import tech.brito.ead.authuser.domain.exceptions.DomainRuleException;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateDTO userDto) {
        var userSaved = userService.findById(id);
        modelMapper.map(userDto, userSaved);
        return userService.save(userSaved);
    }

    @PutMapping("/{id}/password")
    public String updatePassword(@PathVariable UUID id, @RequestBody @Valid PasswordUpdateDTO passwordDTO) {
        var userSaved = userService.findById(id);
        if (!userSaved.getPassword().equals(passwordDTO.getOldPassword())) {
            throw new DomainRuleException("Old password does not match user's current password");
        }

        userSaved.setPassword(passwordDTO.getPassword());
        userService.save(userSaved);
        return "Password updated sucessfully";
    }

    @PutMapping("/{id}/image")
    public String updateImage(@PathVariable UUID id, @RequestBody @Valid ImageUpdateDTO imageDTO) {
        var userSaved = userService.findById(id);
        userSaved.setImageUrl(imageDTO.getImageUrl());
        userService.save(userSaved);
        return "Image updated sucessfully";
    }
}
