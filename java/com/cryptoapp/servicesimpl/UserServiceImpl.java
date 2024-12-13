package com.cryptoapp.servicesimpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.User;
import com.cryptoapp.repository.UserRepo;
import com.cryptoapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        User newUser=new User();
        
        newUser.setUserId(user.getUserId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        return userRepo.save(newUser);
      }

    @Override
    public User getUserById() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public Optional<User> getByEmail(String email) {
       return userRepo.findByEmail(email); 
    }

    @Override
    public boolean userExist(String email) {
     Optional<User> byEmail=  userRepo.findByEmail(email);
     return byEmail.isPresent();
    }

    @Override
    public User findUserProfileByjwt(String jwt)  {
        // TODO Auto-generated method stub
        String username = jwtService.getEmailFromToken(jwt);

        User user=userRepo.findByEmail(username).orElse(null);

        return user;
        
    }

   


}
