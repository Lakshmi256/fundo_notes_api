package com.bridgelabz.fundoonotes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;

@RestController
public class NoteController {
@PostMapping("/note/create")
public ResponseEntity<Response> create (@RequestBody NoteInformation information, @RequestHeader("token") String token){
	return null;
	
}
}
