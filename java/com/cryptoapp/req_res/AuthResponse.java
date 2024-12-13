package com.cryptoapp.req_res;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private String otp;
}
