package com.innowise.auth.security;

import com.innowise.auth.entity.Role;
import com.innowise.auth.entity.RoleName;
import com.innowise.auth.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final String JWT_KEY = System.getenv("JWT_KEY");

    public String generateToken(User user) {
        var decodedKey = JWT_KEY.getBytes();
        var key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        var roles = user.getRoles().stream().map(Role::getName).toList();

        return Jwts.builder()
            .claim("email", user.getEmail())
            .claim("roles", roles)
            .signWith(key)
            .compact();
    }

    public void validateToken(String token) throws JwtException {

        var key = JWT_KEY.getBytes();
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public String fetchEmail(String token) throws JwtException {
        var key = JWT_KEY.getBytes();

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("email")
            .toString();
    }

    public List<RoleName> fetchRoles(String token) {
        var key = JWT_KEY.getBytes();

        var roles = (List<String>) Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("roles");

        return roles.stream().map(RoleName::valueOf).toList();
    }
}
