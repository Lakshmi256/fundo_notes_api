package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UsersDetail;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@RestController

public class UserController {

	@Autowired
	private Services service;

	@Autowired
	private JwtGenerator generate;

	@PostMapping("/user/registration")
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean result = service.register(information);
		if (result) {

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration succefull", 200, information));

		}

		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already ", 400, information));

	}

	@PostMapping("user/login")
	public ResponseEntity<UsersDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			String token = generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login succefull", information.getUsername())
					.body(new UsersDetail(token, 200, information));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsersDetail("login failed ", 400, information));

	}

	@GetMapping("/user/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws Exception {

		System.out.println("token for verification" + token);
		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified", 400, token));

	}

	@PostMapping("user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {
		System.out.println(email);

		boolean result = service.isUserExist(email);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user exist", 200, email));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("user does not exist with given email id", 400, email));

	}
	@PutMapping("user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token,@RequestBody PasswordUpdate update)
	{
		boolean result=service.update(update, token);
		if(result)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("password updated successfully", 200, update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("password  does not match", 402, update));
	}
}
