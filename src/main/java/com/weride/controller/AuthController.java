package com.weride.controller;

import com.weride.dto.ResetPasswordRequest;
import com.weride.dto.Result;
import com.weride.dto.UserActivationRequest;
import com.weride.dto.UserAuthRequest;
import com.weride.model.User;
import com.weride.repository.UserRepository;
import com.weride.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Optional;

import static java.time.ZonedDateTime.now;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserAuthRequest request) {
        log.info("IN AuthController register(): email=[{}]", request.getEmail());

        return userService.register(request);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserAuthRequest request) {
        log.info("IN AuthController login(): email=[{}]", request.getEmail());

        return userService.login(request);
    }

    @PostMapping("/send-verification/{email}")
    public Result sendVerificationEmail(@PathVariable("email")
                                        @NotBlank(message = "should not be blank")
                                        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@ucsd\\.edu$", message = "should be ending with @ucsd.edu")
                                        String email) {
        log.info("IN AuthController sendVerificationEmail(): email=[{}]", email);

        return userService.sendVerificationEmail(email);
    }

    @PostMapping("/verify")
    public Result activateUser(@RequestBody UserActivationRequest request) {
        log.info("IN AuthController activateUser(): email=[{}]", request.getEmail());

        return userService.activateAccount(request);
    }

    @PostMapping("/reset-password")
    public Result resetPassword(@Validated @RequestBody ResetPasswordRequest request) {
        log.info("IN AuthController resetPassword(): email=[{}]", request.getEmail());

        return userService.resetPassword(request);
    }

    @PostMapping("/refresh-token/{token}")
    public Result refresh(@PathVariable ("token")
                          @Validated
                          @NotBlank(message = "should not be blank")
                          String oldRefreshToken) {
        log.info("IN AuthController -> refresh(): {}", now());
        return userService.refreshTokens(oldRefreshToken);
    }

    @GetMapping("/temp/{code}")
    public Optional<User> test(@PathVariable("code") String code) {
        return userRepository.findByRefreshToken(code);
    }
}
