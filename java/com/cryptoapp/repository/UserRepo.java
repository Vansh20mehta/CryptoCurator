package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

 Optional<User> findByEmail(String email);
}
