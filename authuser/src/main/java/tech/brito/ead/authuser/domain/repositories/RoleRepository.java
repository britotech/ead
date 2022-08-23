package tech.brito.ead.authuser.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.brito.ead.authuser.domain.models.Role;
import tech.brito.ead.authuser.enums.RoleType;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleType name);
}
