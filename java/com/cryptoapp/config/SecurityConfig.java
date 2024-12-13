package com.cryptoapp.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.cryptoapp.servicesimpl.SecurityUserDetailService;



import java.util.*;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityUserDetailService securityUserDetailService;

    

    /**
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //userDetailService object 
        daoAuthenticationProvider.setUserDetailsService(securityUserDetailService);
        //password encoder object 
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    
     return new BCryptPasswordEncoder();   
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize->
                     authorize
                     .requestMatchers("/auth/**").permitAll()
                     .anyRequest().permitAll())
                    .addFilterBefore(new JwtFilter(), BasicAuthenticationFilter.class)
                     .csrf(csrf->csrf.disable())
                     .cors(cors->cors.configurationSource(corsConfigurationSource()));

                     return httpSecurity.build();

    }


      
       private CorsConfigurationSource  corsConfigurationSource(){
        return new CorsConfigurationSource() {
            
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                CorsConfiguration config=new CorsConfiguration();
                config.setAllowedOrigins(
                    Arrays.asList("http://localhost:3000","http://localhost:5173")
                    );
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
        };
                
                  
                
    }

}

