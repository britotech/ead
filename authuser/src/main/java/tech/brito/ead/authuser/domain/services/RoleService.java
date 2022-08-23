package tech.brito.ead.authuser.domain.services;

import org.springframework.stereotype.Service;
import tech.brito.ead.authuser.domain.exceptions.RoleNotFoundException;
import tech.brito.ead.authuser.domain.models.Role;
import tech.brito.ead.authuser.domain.repositories.RoleRepository;
import tech.brito.ead.authuser.enums.RoleType;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(RoleType name) {
        return roleRepository.findByName(name).orElseThrow(RoleNotFoundException::new);
    }
}
