package com.cryptoapp.servicesimpl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.crypto.SecretKey;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

    // Replace this with a secure key in a real application, ideally fetched from environment variables
    final String SECRET_KEY="vnvnvnvnvnvnvnvjrutiwierznmbjlkopjewbheehfdhqeeiyiphmxnz";
    SecretKey key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate token with given user name
    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthorities(authorities);
    
        String jwt=Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime()+86400000))
        .claim("email", auth.getName())
        .claim("authorities", roles)
        .signWith(key)
        .compact();

        return jwt;
    }

    
    // Extract the username from the token
    public String getEmailFromToken(String token) {
        try {
            // Remove "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            // Parse the JWT and get the claims
            Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody(); // Get the claims directly from the Jws object
            
            return claims.get("email", String.class); // Extract email safely
        } catch (Exception e) {
            // Log the error if needed
            log.error("Error parsing JWT token", e);
        }
        return token;
    }
    

  

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth=new HashSet<>();
        for(GrantedAuthority ga:authorities){
            auth.add(ga.getAuthority());
        }    
        return String.join(",", auth);
    }



   
}
   

   

    

   

    

