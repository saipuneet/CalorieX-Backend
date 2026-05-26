package com.CalorieX.CalorieX_Backend.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    private Key getSignInKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    public String generateToken(String email) {
          return Jwts.builder()
                  .setSubject(email)
                  .setIssuedAt(new Date())
                  .setExpiration(
                          new Date(
                                  System.currentTimeMillis() + 1000 * 60 * 60 * 24
                          )
                  )
                  .signWith(
                          getSignInKey(),
                          SignatureAlgorithm.HS256)
                  .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){

        return extractAllClaims(token).getSubject();
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());

    }

    public boolean isTokenValid(String token, UserDetails userDetails){

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }
}
