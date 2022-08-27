package tech.brito.ead.authuser.core.configs.security;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.brito.ead.authuser.domain.exceptions.ValidationJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwtStr = getTokenHeader(request);
            validateJwt(jwtStr);
            var username = jwtProvider.getUsernameJwt(jwtStr);
            var userDetails = userDetailsService.loadUserByUsername(username);
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
        if (StringUtils.isNotBlank(headerAuth) && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    private void validateJwt(String jwtStr) {
        if (StringUtils.isBlank(jwtStr)) {
            throw new ValidationJwtException("Authentication error: Invalid JWT");
        }

        jwtProvider.validateJwt(jwtStr);
    }
}
