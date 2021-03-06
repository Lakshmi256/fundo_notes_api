package com.bridgelabz.fundoonotes.controller;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.dto.ForgotPassword;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UsersDetail;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private Services service;

	@Autowired
	private JwtGenerator generate;

	/* API for registration */

	@PostMapping("/users/register")
	@ApiOperation(value = "Api to register for users in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> register(@RequestBody UserDto information) {
		boolean result = service.register(information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration successfull", information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already exist", information));
	}

	/* API for Login */
	@PostMapping("users/login")
	@ApiOperation(value = "Api to login for user in  Fundoonotes", response = Response.class)
	public ResponseEntity<UsersDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			String token = generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull",token)
					.body(new UsersDetail(token, information));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsersDetail("login failed ", information));

	}

	/* API for for user verification */
	@GetMapping("users/verify/{token}")
	@ApiOperation(value = "Api to verify email of user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws Exception {

		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", token));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("not verified", token));
	}

	/* API for for forgot password */
	@PostMapping("users/forgotpassword")
	@ApiOperation(value = "Api to forgot password of user  for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPassword user) {
		System.out.println(user.getEmail());
		long  userid = service.forgotPassword(user.getEmail());
		if (userid!=0) {
			String token = generate.jwtToken(userid);
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", user.getEmail())
					.body(new Response(token, user));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(new Response("user does not exist with given email id", user));

	}

	/* API for for updating password with token */
	@PutMapping("users/updatePassword/{token}")
	@ApiOperation(value = "Api to update user details for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> update(@RequestHeader("token") String token, @RequestBody PasswordUpdate update) {
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("password updated successfully", update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("password  does not match", update));
	}

	/* API for getting all user details */
	@GetMapping("users")
	@ApiOperation(value = "Api to get all users for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> users = service.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("the users are", users));
	}

	/* API for getting details of only one user */
	@GetMapping("user")
	@ApiOperation(value = "Api to get one user for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getOneUser(@RequestHeader("token") String token) {
		UserInformation user = service.getsingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user is ", user));
	}

	/* Api for adding profile picture */
	@PostMapping("/profile/add")
	@ApiOperation(value = "Api to upload profile pic of User for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profile = service.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		if (profile.getUserLabel() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", profile));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", profile));
	}

	/* api for updating profile picture */
	@PutMapping("/profile/update")
	@ApiOperation(value = "Api to update profile pic of User for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> updateProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profile = service.update(file, file.getOriginalFilename(), file.getContentType(), token);
		if (profile.getUserLabel() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", profile));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", profile));
	}

	/* api for fetching profile picture */
	@GetMapping("/profile/get")
	@ApiOperation(value = "Api to get profile pic of User for Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token) {
		S3Object profile = service.getProfilePic(token);
		if (profile != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", profile));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", profile));
	}
	/* API for adding a collaborator */
	@PostMapping("/collabrator/add")
	@ApiOperation(value = "Api to add collaborator of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addCollab(@RequestParam("email") String email, @RequestParam("noteId") Long noteId,
			@RequestHeader("token") String token) {
		service.addcolab(noteId, email, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created"));
	}

	/* API for removing a collaborator */
	@DeleteMapping("/collabrator/remove")
	@ApiOperation(value = "Api to remove collaborator of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> removeCollab(@RequestParam("email") String email,
			@RequestParam("noteId") Long noteId, @RequestHeader("token") String token) {
		service.removeCollab(noteId, token, email);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created"));
	}

	/* API for getting all collaborators */
	@GetMapping("/getallcollab")
	@ApiOperation(value = "Api to get all collaborators of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getAllCollab(@RequestHeader("token") String token) {
		List<NoteInformation> notes = service.getAllCollabs(token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", notes));
	}

}
