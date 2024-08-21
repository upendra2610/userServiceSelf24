package org.example.userservice24.controllers;

import org.example.userservice24.dtos.ResponseStatus;
import org.example.userservice24.dtos.*;
import org.example.userservice24.models.Token;
import org.example.userservice24.models.User;
import org.example.userservice24.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        User user = userService.signup(
                signupRequestDto.getName(),
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword());
        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setUserName(user.getName());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return responseDto;

    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = userService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }

    @PostMapping("/validate")
    public UserDto validateToken(@RequestHeader("Authorization") String token){
        User user = userService.validateToken(token);

        return UserDto.fromUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
