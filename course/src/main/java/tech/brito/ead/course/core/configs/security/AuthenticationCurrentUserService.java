package tech.brito.ead.course.core.configs.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationCurrentUserService {

    public UserDetailsImpl getCurrentUSer() {
        return (UserDetailsImpl) getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void validatePermission(UUID userId) {
        var currentUser = getCurrentUSer();
        if (!currentUser.containsAdminPermission() && !currentUser.getUserId().equals(userId)) {
            throw new AccessDeniedException("Forbidden");
        }
    }
}