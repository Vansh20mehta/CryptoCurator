package com.cryptoapp.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.cryptoapp.servicesimpl.JwtService;
import com.cryptoapp.servicesimpl.SecurityUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

        @Autowired
        private  JwtService jwtService;

        @Autowired
        private SecurityUserDetailService securityUserDetailService  ;

  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                final String SECRET_KEY="vnvnvnvnvnvnvnvjrutiwierznmbjlkopjewbheehfdhqeeiyiphmxnz";
                    
                String jwt = request.getHeader("Authorization");
              

                if (jwt != null) {
            jwt = jwt.substring(7); // Extract token

            try {
                SecretKey key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                Claims claims=(Claims) Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt);
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> authList=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth= new UsernamePasswordAuthenticationToken(
                     email,
                     null
                     ,authList
                     );

                     SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                logger.error("Jwt Error: ", e);
              
            }
         
        }


                   
       

        // Continue the filter chain
        filterChain.doFilter(request, response);

                   

  }
            

}
