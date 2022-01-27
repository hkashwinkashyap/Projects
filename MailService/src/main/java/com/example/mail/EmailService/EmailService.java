package com.example.mail.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService implements EmailServiceInterface {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Async
	@Override
	public void sendEmail(String toEmail) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("ashwinkashyapmunna@gmail.com");
		message.setTo(toEmail);
		message.setText("Hi. Error!");
		message.setSubject("Congrats!!");

		mailSender.send(message);
		logger.info("Mail Sent to " + toEmail);
	}

}