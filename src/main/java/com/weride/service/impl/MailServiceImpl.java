package com.weride.service.impl;

import com.weride.service.MailService;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
	@Resource
	private MailProperties mailProperties;
	@Resource
	private JavaMailSender javaMailSender;

	@Override
	public boolean sendWithHtml(String to, String subject, String html) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(mailProperties.getUsername());
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(html, true);

			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			log.error("send html mail error: ", e);
			return false;
		}
		return true;
	}
}
