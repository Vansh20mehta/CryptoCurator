package com.cryptoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserDto {
    private String jwt;
    private String email;
    private String password;
}
