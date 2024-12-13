package com.cryptoapp.services;

import java.util.Optional;

import com.cryptoapp.entities.User;

public interface UserService {

    public User saveUser(User user);

    public User getUserById();

 Optional<User> getByEmail(String email);

 boolean userExist(String email);

 public User findUserProfileByjwt(String jwt);






}
