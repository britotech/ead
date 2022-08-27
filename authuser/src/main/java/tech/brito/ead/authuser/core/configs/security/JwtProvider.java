package tech.brito.ead.authuser.core.configs.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tech.brito.ead.authuser.domain.exceptions.ValidationJwtException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Log4j2
public class JwtProvider {

    @Value("${ead.auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateJwt(Authentication authentication) {
        var userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        var expiration = new Date((new Date()).getTime() + jwtExpirationMs);

        return Jwts
                .builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public void validateJwt(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            throw new ValidationJwtException("Authentication error: Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            throw new ValidationJwtException("Authentication error: Invalid JWT signature", ex);
        } catch (ExpiredJwtException ex) {
            throw new ValidationJwtException("Authentication error: JWT token is expired", ex);
        }
    }
}
