package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfilePic;

@RestController
public class ProfilePicController {
	@Autowired
	private ProfilePic service;

	@PostMapping("/profile/add")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profile = service.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		return profile.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", 200, profile))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", 400));
	}

	@PutMapping("/profile/update")
	public ResponseEntity<Response> updateProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profile = service.update(file, file.getOriginalFilename(), file.getContentType(), token);
		return profile.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", 200, profile))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", 400));
	}

	@GetMapping("/profile/get")
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token) {
		S3Object profile = service.getProfilePic(token);
		return profile != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("profile added successful", 200, profile))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went wrong ", 400));
	}
}
