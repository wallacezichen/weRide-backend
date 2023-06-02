package com.weride.service.impl;

import com.weride.dto.ResetPasswordRequest;
import com.weride.dto.Result;
import com.weride.dto.UserActivationRequest;
import com.weride.dto.UserAuthRequest;
import com.weride.model.User;
import com.weride.repository.UserRepository;
import com.weride.security.jwt.JWTProvider;
import com.weride.service.MailService;
import com.weride.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.weride.security.jwt.JWTType.ACCESS;
import static com.weride.security.jwt.JWTType.REFRESH;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final MailService mailService;
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTProvider jwtProvider;

    @Autowired
    public UserServiceImpl(MailService mailService, UserRepository userRepository, JWTProvider jwtProvider) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    @Transactional
    public Result register(UserAuthRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        LocalDateTime now = LocalDateTime.now();
        String code = generateCode();

        User user = byEmail.orElseGet(() ->
                User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .isActivated(false)
                        .createDate(now)
                        .lastUpdateAtDate(now)
                        .build()
        );
        log.info(user.getEmail());
        if (user.getIsActivated()) {
            log.info("register: email exist: {}", request);
            return Result.fail("Email exists");
        }

        user.setVerificationCode(code);
        user.setVerificationCodeTime(now);
        userRepository.save(user);
        sendVerificationEmail(request.getEmail(), code);

        log.info("register success: {}", request);
        return Result.ok();
    }

    @Override
    public Result login(UserAuthRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return Result.fail("Email is not registered");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Result.fail("Email or password is incorrect");
        }

        Map<String, String> tokens = generateTokens(user.getEmail());
        user.setRefreshToken(tokens.get("refreshToken"));
        userRepository.save(user);

        return Result.ok(tokens);
    }


    @Override
    @Transactional
    public Result sendVerificationEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        byEmail.ifPresent(user -> {
            String code = generateCode();
            user.setVerificationCode(code);
            user.setVerificationCodeTime(LocalDateTime.now());
            sendVerificationEmail(email, code);
            userRepository.save(user);
        });

        return byEmail
                .map(temp -> Result.ok())
                .orElse(Result.fail("Email does not exist"));
    }

    @Override
    @Transactional
    public Result resetPassword(ResetPasswordRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        Result result = checkVerificationCode(optionalUser, request.getCode());
        if (result != null) {
            return result;
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result activateAccount(UserActivationRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        Result result = checkVerificationCode(optionalUser, request.getCode());
        if (result != null) {
            return result;
        }

        User user = optionalUser.get();
        user.setIsActivated(true);
        userRepository.save(user);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result refreshTokens(String oldRefreshToken) {
        log.info(oldRefreshToken);
        Optional<User> byRefreshToken = userRepository.findByRefreshToken(oldRefreshToken);
        if (byRefreshToken.isEmpty()) {
            return Result.fail("Refresh token not found");
        }

        User user = byRefreshToken.get();
        Map<String, String> tokens = generateTokens(user.getEmail());
        user.setRefreshToken(tokens.get("refreshToken"));
        userRepository.save(user);
        return Result.ok(tokens);
    }


    private static String generateCode() {
        SecureRandom random = new SecureRandom();
        String code = String.valueOf(random.nextInt(9000) + 1000);
        return code;
    }

    private void sendVerificationEmail(String email, String code) {
        String subject = "WeRide account activate";
        mailService.sendWithHtml(email, subject, code);
    }

    private Map<String, String> generateTokens(String email) {
        log.info("IN UserService -> generatesTokens(): gen tokens for user[email:{}]", email);

        String accessToken = jwtProvider.generateToken(ACCESS, email);
        String accessExpiration = jwtProvider.getExpirationDate(accessToken, ACCESS).toString();
        String refreshToken = jwtProvider.generateToken(REFRESH, email);
        String refreshExpiration = jwtProvider.getExpirationDate(refreshToken, REFRESH).toString();

        return Map.of(
                "accessToken", accessToken,
                "accessTokenExpiration", accessExpiration.substring(0, accessExpiration.indexOf("[")),
                "refreshToken", refreshToken,
                "refreshTokenExpiration", refreshExpiration.substring(0, refreshExpiration.indexOf("["))
        );
    }

    private Result checkVerificationCode(Optional<User> optionalUser, String code) {
        if (optionalUser.isEmpty()) {
            return Result.fail("Email does not exist");
        }

        User user = optionalUser.get();
        Duration duration = Duration.between(user.getVerificationCodeTime(), LocalDateTime.now());

        if (duration.toHours() > 24) {
            return Result.fail("Verification code is expired");
        }

        if (!code.equals(user.getVerificationCode())) {
            return Result.fail("Verification code is not correct");
        }

        return null;
    }
}
