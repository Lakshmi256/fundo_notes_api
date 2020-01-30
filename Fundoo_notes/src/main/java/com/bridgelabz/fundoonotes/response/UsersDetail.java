package com.bridgelabz.fundoonotes.response;

import lombok.Data;

@Data
public class UsersDetail {
	private String token;
	private int statuscode;
	private Object obj;

	public UsersDetail(String token, int statuscode, Object obj) {
		this.obj = obj;
		this.statuscode = statuscode;
		this.token = token;
	}
}
