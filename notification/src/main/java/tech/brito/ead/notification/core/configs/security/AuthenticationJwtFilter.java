package tech.brito.ead.notification.core.configs.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.brito.ead.notification.domain.exceptions.ValidationJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Log4j2
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwtStr = getTokenHeader(request);
            validateJwt(jwtStr);

            var userId = jwtProvider.getSubjectJwt(jwtStr);
            var rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles");
            var userDetails = UserDetailsImpl.build(UUID.fromString(userId), rolesStr);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ValidationJwtException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error("Authentication error: Cannot set User Authentication:{}", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenHeader(HttpServletRequest request) {

        var headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    private void validateJwt(String jwtStr) {
        if (!StringUtils.hasText(jwtStr)) {
            throw new ValidationJwtException("Authentication error: Invalid JWT");
        }

        jwtProvider.validateJwt(jwtStr);
    }
}
