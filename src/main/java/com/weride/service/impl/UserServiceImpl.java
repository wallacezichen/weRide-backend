package com.weride.service.impl;

import com.weride.model.User;
import com.weride.repository.UserRepository;
import com.weride.service.MailService;
import com.weride.service.UserService;
import com.weride.utils.JwtUtil;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
		if (!byEmail.isEmpty()) {
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

		Optional<User> byEmailAndPassword = userRepository.findByEmail(user.getEmail());
		if (byEmailAndPassword.isEmpty()) {
			return new ResponseEntity<>("Your input is not correct", HttpStatus.BAD_REQUEST);
		}

		User temp = byEmailAndPassword.get();
		if (!passwordEncoder.matches(user.getPassword(), temp.getPassword())) {
			return new ResponseEntity<>("Your input is not correct", HttpStatus.BAD_REQUEST);
		}

		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("password", user.getPassword());
		return new ResponseEntity<>(JwtUtil.encode(claims), HttpStatus.OK);
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
		Optional<User> optionalUserFound = userRepository.findByVerificationCodeAndEmail(user.getVerificationCode(),
		   user.getEmail());
		if (optionalUserFound.isEmpty()) {
			return new ResponseEntity<>("Your code is wrong", HttpStatus.BAD_REQUEST);
		}

		User userFound = optionalUserFound.get();

		long diffInMillis = Math.abs(new Date().getTime() - user.getVerificationCodeTime().getTime());
		long diffInHours = diffInMillis / millisToSeconds;
		if (diffInHours > SecondsIn24Hours) {
			return new ResponseEntity<>("Your code is expired", HttpStatus.BAD_REQUEST);
		}

		user.setIsActivated(true);
		userRepository.save(user);
		return new ResponseEntity<>("Activate success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> update(User user) {
		ResponseEntity<String> checkUserResult = checkUser(user);
		if (checkUserResult != null) {
			return checkUserResult;
		}

		Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
		if (byEmail.isEmpty()) {
			return new ResponseEntity<>("User do not exist", HttpStatus.BAD_REQUEST);
		}

		User userInData = byEmail.get();
		// TODO: assign new value

		userRepository.save(userInData);
		return new ResponseEntity<>("update success", HttpStatus.OK);
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
		//		String hashedPassword = passwordEncoder.encode(user.getPassword());
		//		user.setPassword(hashedPassword);
		return user;
	}
}
