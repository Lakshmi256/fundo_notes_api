package com.bridgelabz.fundoonotes.exception;

public class Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	private Exception exception;

	public Exception(String message,Exception exception) {
		super();
		this.message = message;
		this.exception=exception;
	}
}
