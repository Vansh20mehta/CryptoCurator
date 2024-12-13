package com.cryptoapp.servicesimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.Providers;
import com.cryptoapp.entities.User;
import com.cryptoapp.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecurityUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("not found"+username));
     
       List<GrantedAuthority> authList=new ArrayList<>();

       return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authList);
    }



   }