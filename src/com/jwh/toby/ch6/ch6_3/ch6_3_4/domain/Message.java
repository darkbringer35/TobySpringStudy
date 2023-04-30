package com.jwh.toby.ch6.ch6_3.ch6_3_4.domain;

public class Message {
	String text;

	private Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static Message newMessage(String text) {
		return new Message(text);
	}
}
