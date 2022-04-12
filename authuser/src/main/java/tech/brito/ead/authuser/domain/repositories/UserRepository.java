package tech.brito.ead.authuser.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.authuser.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
