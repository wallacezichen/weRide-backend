package com.weride.service;

public interface MailService {
	boolean sendWithHtml(String to, String subject, String html);
}
