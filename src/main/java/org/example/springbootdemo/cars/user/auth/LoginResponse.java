package org.example.springbootdemo.cars.user.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.springbootdemo.cars.user.persistance.AppUser;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;


}
