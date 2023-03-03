package com.weride.service;

import com.weride.model.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
	ResponseEntity<String> register(User user);

	ResponseEntity<String> login(User user);

	void sendVerificationEmail(User user);
	void sendResetPasswordEmail(User user);

	ResponseEntity<String> activateAccount(User user);

	ResponseEntity<String> update(User user);

	ResponseEntity<String> resetPassword(User user);
}
