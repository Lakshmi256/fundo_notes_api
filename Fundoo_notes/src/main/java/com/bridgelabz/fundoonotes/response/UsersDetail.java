package com.bridgelabz.fundoonotes.response;
/*
 * author:Lakshmi Prasad A
 */
import lombok.Data;

@Data
public class UsersDetail {
	private String token;
	private int statuscode;
	private Object obj;

	public UsersDetail(String token, int statuscode, Object obj) {
		this.setObj(obj);
		this.setStatuscode(statuscode);
		this.setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
