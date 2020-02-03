package com.bridgelabz.fundoonotes.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class MailObject implements Serializable {
	private static final long serialVesrionUID=1L;
	private String email;
	private String subject;
	private String message;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
