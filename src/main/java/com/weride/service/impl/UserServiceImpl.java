package com.weride.service.impl;

import com.weride.model.User;
import com.weride.repository.UserRepository;
import com.weride.service.MailService;
import com.weride.service.UserService;
import com.weride.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final MailService mailService;
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public final int millisToSeconds = 1000;
    public final int SecondsIn24Hours = 24 * 60 * 60;

    @Autowired
    public UserServiceImpl(MailService mailService, UserRepository userRepository) {
        this.mailService = mailService;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public ResponseEntity<String> register(User user) {
        ResponseEntity<String> checkUserResult = checkUser(user);
        if (checkUserResult != null) {
            return checkUserResult;
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            return new ResponseEntity<>("Email exists", HttpStatus.BAD_REQUEST);
        }

        encryptPassword(user);

        // verification part
        user.setIsActivated(false);

        sendVerificationEmail(user);

        userRepository.save(user);
        return new ResponseEntity<>("Register success", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> login(User user) {
        ResponseEntity<String> checkUserResult = checkUser(user);
        if (checkUserResult != null) {
            return checkUserResult;
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        return byEmail.map((User temp) -> {

            if (!passwordEncoder.matches(user.getPassword(), temp.getPassword())) {
                return new ResponseEntity<>("Your input is not correct", HttpStatus.BAD_REQUEST);
            }
            // jwt
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", user.getEmail());
            claims.put("password", user.getPassword());
            return new ResponseEntity<>(JwtUtil.encode(claims), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Your input is not correct", HttpStatus.BAD_REQUEST));
    }


    @Override
    public void sendVerificationEmail(User user) {
        ResponseEntity<String> checkUserResult = checkUser(user);
        if (checkUserResult != null) {
            return;
        }

        SecureRandom random = new SecureRandom();
        String code = String.valueOf(random.nextInt(9000) + 1000);
        user.setVerificationCode(code);
        user.setVerificationCodeTime(new Date());

        String subject = "WeRide account activate";
        // TODO: update content
        String content = code;
        mailService.sendWithHtml(user.getEmail(), subject, content);
    }

    @Override
    public ResponseEntity<String> activateAccount(User user) {
        Optional<User> optionalUserFound = userRepository.findByVerificationCodeAndEmail(
                user.getVerificationCode(),
                user.getEmail());

        return optionalUserFound.map(userFound -> {
            long diffInMillis = Math.abs(new Date().getTime() - user.getVerificationCodeTime().getTime());
            long diffInHours = diffInMillis / millisToSeconds;
            if (diffInHours > SecondsIn24Hours) {
                return new ResponseEntity<>("Your code is expired", HttpStatus.BAD_REQUEST);
            }

            user.setIsActivated(true);
            userRepository.save(user);
            return new ResponseEntity<>("Activate success", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Your code is wrong", HttpStatus.BAD_REQUEST));
    }

    @Override
    public ResponseEntity<String> update(User user) {
        ResponseEntity<String> checkUserResult = checkUser(user);
        if (checkUserResult != null) {
            return checkUserResult;
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        return byEmail.map(userInDB -> {
            if (!user.getPassword().equals("")){
                userInDB.setPassword(user.getPassword());
                encryptPassword(userInDB);
            }
            userRepository.save(userInDB);

            // TODO: assign new value

            return new ResponseEntity<>("update success", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST));
    }

    @Override
    public ResponseEntity<String> resetPassword(User user){
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        return byEmail.map(userInDB -> {
            if (user.getVerificationCode().equals(userInDB.getVerificationCode())){
                if (!user.getPassword().equals("")){
                    userInDB.setPassword(user.getPassword());
                    encryptPassword(userInDB);
                }
                else{
                    return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
                }
                userRepository.save(userInDB);
                return new ResponseEntity<>("Successfully update", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Verification code does not match", HttpStatus.BAD_REQUEST);
            }
        }).orElseGet(() -> new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<String> checkUser(User user) {
        if (user == null) {
            return new ResponseEntity<>("User cannot be null", HttpStatus.BAD_REQUEST);
        }

        if (user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>("Email address and password is needed.", HttpStatus.BAD_REQUEST);
        }

        String domain = user.getEmail().split("@")[1];
        if (!"ucsd.edu".equals(domain)) {
            return new ResponseEntity<>("Email should be end with ucsd.edu", HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    private User encryptPassword(User user) {

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return user;
    }
}
