package com.bridgelabz.fundoonotes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import io.swagger.models.Response;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

	@Autowired
	private Services service;
	
	@Autowired
	private JwtGenerator generate;
	
	
	@PostMapping("/user/registration")
	@CachePut(value="user", key="#token")
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean result = service.register(information);
		if (result) {
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response());

		} else {
                  
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response());

		}
	}
	@PostMapping
	public ResponseEntity<UsersDetail>login(@RequestBody LoginInformation information){
		UserInformation userInformation=service.login(information);
	if (userInformation!=null)
	{
		String token=generate.jwtToken(userInformation.getUserId());
	}
	}
}
