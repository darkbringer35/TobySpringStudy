package com.jwh.toby.ch6.ch6_5.ch6_5_7.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
	@Override
	public void send(SimpleMailMessage simpleMailMessage) throws MailException {

	}

	@Override
	public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {

	}
}
