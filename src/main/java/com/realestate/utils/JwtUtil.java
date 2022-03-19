package com.realestate.utils;

import com.realestate.model.dto.UserDTO;
import com.realestate.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "hemsiemlak-patika-realy-secret-key-hemsiemlak-patika-realy-secret-key";

    private static final long EXPIRATION_TIME = 300_000;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserName(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return this.getExpirationDate(token).before(new Date());
    }

    public String generateToken(UserEntity userEntity) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userEntity.getUserType().toString());
        claims.put("email", userEntity.getEmail());
        claims.put("id", userEntity.getId());
        claims.put("tugba", "yavuz");
        claims.put("service", "real-estate-auth");

        long now = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userEntity.getEmail())
                .setIssuedAt(new Date())
                .setIssuer("emlakburada")
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

}
