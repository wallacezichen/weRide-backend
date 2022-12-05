package com.weride.controller;

import com.weride.model.User;
import com.weride.service.MailService;
import com.weride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * port:8080
 * POST: localhost:8080/api/user-service/login
 */
@RestController
@RequestMapping("/api/user-service")
public class UserController {
	@Autowired
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		return new ResponseEntity<>("Register success", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		return new ResponseEntity<>("Login success", HttpStatus.OK);
	}

	@PostMapping("/verification/{code}")
	public void activateUser(@PathVariable(value = "code") String code) {

	}
}
