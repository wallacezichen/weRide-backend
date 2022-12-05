package com.weride.service.impl;

import com.weride.service.MailService;
import com.weride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {
	@Autowired
	private final MailService mailService;

	public UserServiceImpl(MailService mailService) {
		this.mailService = mailService;
	}
}
