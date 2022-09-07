package tech.brito.ead.authuser.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.authuser.api.clients.CourseClient;
import tech.brito.ead.authuser.api.models.UserEventDTO;
import tech.brito.ead.authuser.core.publishers.UserEventPublisher;
import tech.brito.ead.authuser.domain.exceptions.DomainRuleException;
import tech.brito.ead.authuser.domain.exceptions.UserNotFoundException;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.repositories.UserRepository;
import tech.brito.ead.authuser.enums.ActionType;
import tech.brito.ead.authuser.enums.RoleType;
import tech.brito.ead.authuser.enums.UserType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CourseClient courseClient;
    private final UserEventPublisher userEventPublisher;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       RoleService roleService,
                       CourseClient courseClient,
                       UserEventPublisher userEventPublisher,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.courseClient = courseClient;
        this.userEventPublisher = userEventPublisher;
        this.modelMapper = modelMapper;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User saveUser(User user) {
        addRoleToUser(user, RoleType.ROLE_STUDENT);

        user = validateAndSave(user);
        publisherUserEvent(user, ActionType.CREATE);
        return user;
    }

    private void addRoleToUser(User user, RoleType roleType) {
        var role = roleService.findByName(roleType);
        user.getRoles().add(role);
    }

    private void publisherUserEvent(User user, ActionType actionType) {
        var userDTO = modelMapper.map(user, UserEventDTO.class);
        userEventPublisher.publishUserEvent(userDTO, actionType);
    }

    @Transactional
    public User subscriptionInstructor(User user) {
        user.setType(UserType.INSTRUCTOR);
        addRoleToUser(user, RoleType.ROLE_INSTRUCTOR);

        user = userRepository.save(user);
        publisherUserEvent(user, ActionType.UPDATE);
        return user;
    }

    @Transactional
    public User updateUser(User user) {
        user = validateAndSave(user);
        publisherUserEvent(user, ActionType.UPDATE);
        return user;
    }

    @Transactional
    public User updatePassword(User user) {
        return userRepository.save(user);
    }

    private User validateAndSave(User user) {
        validateEmailAllowed(user);
        validateUsernameAllowed(user);

        return userRepository.save(user);
    }

    private void validateEmailAllowed(User user) {
        var optionalSavedUser = userRepository.findByEmail(user.getEmail());

        if (optionalSavedUser.isPresent() && !optionalSavedUser.get().equals(user)) {
            throw new DomainRuleException(String.format("There is already a registered user with the email %s", user.getEmail()));
        }
    }

    private void validateUsernameAllowed(User user) {
        var optionalSavedUser = userRepository.findByUsername(user.getUsername());

        if (optionalSavedUser.isPresent() && !optionalSavedUser.get().equals(user)) {
            throw new DomainRuleException(String.format("There is already a registered user with the username %s", user.getEmail()));
        }
    }

    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
        publisherUserEvent(user, ActionType.DELETE);
    }
}
