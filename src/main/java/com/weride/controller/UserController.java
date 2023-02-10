package com.weride.controller;

import com.weride.model.User;
import com.weride.repository.UserRepository;
import com.weride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * port:8080 POST: localhost:8080/api/user-service/login
 */
@RestController
@RequestMapping("/api/user-service")
public class UserController {
	private final UserService userService;
	private UserRepository userRepository;

	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		return userService.register(user);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		return userService.login(user);
	}

	@GetMapping("/resend-verification")
	public void resentVerificationEmail(@RequestBody User user) {
		userService.sendVerificationEmail(user);
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user) {
		return userService.update(user);
	}

	@PostMapping("/verification")
	public ResponseEntity<String> activateUser(@RequestBody User user) {
		return userService.activateAccount(user);
	}
}
