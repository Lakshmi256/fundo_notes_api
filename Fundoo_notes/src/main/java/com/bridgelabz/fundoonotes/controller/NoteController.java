package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;

import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService service;

	@PostMapping("/note/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto information, @RequestHeader("token") String token) {
		service.createNote(information, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, information));
	}

	@PutMapping("/note/update")
	public ResponseEntity<Response> update(@RequestBody NoteUpdation note, @RequestHeader("token") String token) {
		service.updateNote(note, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated ", 201, note));
	}
}
