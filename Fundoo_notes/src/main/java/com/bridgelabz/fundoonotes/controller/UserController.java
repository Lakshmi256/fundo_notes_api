package com.bridgelabz.fundoonotes.controller;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UsersDetail;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import io.swagger.annotations.ApiOperation;

@RestController

public class UserController {

	@Autowired
	private Services service;

	@Autowired
	private JwtGenerator generate;

	/* API for registration */

	@PostMapping("/users/register")
	@ApiOperation(value = "Api to register for users in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> register(@RequestBody UserDto information) {
		boolean result = service.register(
				information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration successfull", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("user already exist", 400, information));
	}

	/* API for Login */
	@PostMapping("users/login")
	@ApiOperation(value = "Api to login for user in  Fundoonotes", response = Response.class)
	public ResponseEntity<UsersDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			String token = generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", information.getEmail())
					.body(new UsersDetail(token, 200, information));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsersDetail("login failed ", 400, information));

	}

	/* API for for user verification */
	@GetMapping("users/verify/{token}")
	@ApiOperation(value = "Api to verify email of user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws Exception {

		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("not verified", 400, token));
	}

	/* API for for forgot password */
	@PostMapping("users/forgotpassword")
	@ApiOperation(value = "Api to forgot password of user  for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {

		boolean result = service.forgotPassword(email);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user exist", 200, email));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(new Response("user does not exist with given email id", 400, email));

	}

	/* API for for updating password with token */
	@PutMapping("users/updatePassword/{token}")
	@ApiOperation(value = "Api to update user details for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody PasswordUpdate update) {
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("password updated successfully", 200, update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("password  does not match", 402, update));
	}

	/* API for getting all user details */
	@GetMapping("users")
	@ApiOperation(value = "Api to get all users for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> users = service.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("the users are", 200, users));
	}

	/* API for getting details of only one user */
	@GetMapping("user")
	@ApiOperation(value = "Api to get one user for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getOneUser(@RequestHeader("token") String token) {
		UserInformation user = service.getsingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user is ", 200, user));
	}
}
