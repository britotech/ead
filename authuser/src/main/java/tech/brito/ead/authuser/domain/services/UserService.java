package tech.brito.ead.authuser.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.brito.ead.authuser.domain.exceptions.DomainRuleException;
import tech.brito.ead.authuser.domain.exceptions.UserNotFoundException;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User save(User user) {
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
}
