package com.cryptoapp.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDto {
    @NotBlank(message = "Invalid email")
    private String name;
    @NotBlank(message = "Invalid email")
    private String email;
    @NotBlank(message = "Invalid email")
    private String password;
    @NotBlank(message = "Invalid email")
    private String phonenumber;
}
