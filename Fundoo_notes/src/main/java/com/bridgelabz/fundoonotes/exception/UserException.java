package com.bridgelabz.fundoonotes.exception;
/*
 * author:Lakshmi Prasad A
 */
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public UserException(String message) {
		super(message);
		this.message = message;
	}
}
