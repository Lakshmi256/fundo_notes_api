package com.bridgelabz.fundoonotes.utility;

/*
 * author:Lakshmi Prasad A
 */
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtGenerator {
	private static final String SECRET = "1234567890";

	public String jwtToken(long id) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", id).sign(Algorithm.HMAC512(SECRET));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return token;
	}

	public Long parseJWT(String jwt) {
		Long userId = (long) 0;
		if (jwt != null) {
			userId = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwt).getClaim("id").asLong();
		}
		return userId;
	}
}
