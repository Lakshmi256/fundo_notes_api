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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
