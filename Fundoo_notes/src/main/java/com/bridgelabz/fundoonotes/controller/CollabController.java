package com.bridgelabz.fundoonotes.controller;

/*
 * author:Lakshmi Prasad A
 */
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
import com.bridgelabz.fundoonotes.service.ColabratorService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CollabController {
	@Autowired
	private ColabratorService service;

	/* API for adding a collaborator */
	@PostMapping("/collabrator/add")
	@ApiOperation(value = "Api to add collaborator of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addCollab(@RequestParam("email") String email, @RequestParam("noteId") Long noteId,
			@RequestHeader("token") String token) {
		service.addcolab(noteId, email, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
	}

	/* API for removing a collaborator */
	@DeleteMapping("/collabrator/remove")
	@ApiOperation(value = "Api to remove collaborator of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> removeCollab(@RequestParam("email") String email,
			@RequestParam("noteId") Long noteId, @RequestHeader("token") String token) {
		service.removeCollab(noteId, token, email);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
	}

	/* API for getting all collaborators */
	@GetMapping("/getallcollab")
	@ApiOperation(value = "Api to get all collaborators of a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getAllCollab(@RequestHeader("token") String token) {
		List<NoteInformation> notes = service.getAllCollabs(token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, notes));
	}

}
