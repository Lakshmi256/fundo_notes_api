package com.bridgelabz.fundoonotes.response;
/*
 * author:Lakshmi Prasad A
 */
import org.springframework.stereotype.Component;

@Component
public class MailResponse {
	public String fromMessage(String url, String token) {
		return url + "/" + token;
	}
}
