package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;

@RestController
public class CollabController {
	@Autowired
	private CollabController service;

	/* API for adding a collabrator */
	@PostMapping("/collabrator/add")
	public ResponseEntity<Response> addCollab(@RequestParam("email") String email,
			@RequestParam("noteId") String noteId, @RequestHeader("token") String token) {
		service.addCollab(email, noteId, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
	}

	/* API for removing a collabrator */
	@DeleteMapping("/collabrator/remove")
	public ResponseEntity<Response> removeCollab(@RequestParam("email") String email,
			@RequestParam("noteId") String noteId, @RequestHeader("token") String token) {
		service.removeCollab(email, noteId, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
	}

	@GetMapping("/getallcollab")
	public ResponseEntity<Response> getAllCollab(@RequestHeader("token") String token) {
		List<NoteInformation> notes = (List<NoteInformation>) service.getAllCollab(token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, notes));
	}

}
