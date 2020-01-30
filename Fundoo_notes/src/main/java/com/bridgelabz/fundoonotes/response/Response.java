package com.bridgelabz.fundoonotes.response;

import lombok.Data;

@Data
public class Response {
	private String message;
	private int statuscode;
	private Object obj;

	public Response(String message, int statusCode, Object obj) {
		this.message = message;
		this.statuscode = statusCode;
		this.obj = obj;
	}

}
