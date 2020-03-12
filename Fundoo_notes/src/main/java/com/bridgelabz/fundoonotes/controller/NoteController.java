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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class NoteController {

	@Autowired
	private NoteService service;

	/* API for creating a Note */
	@PostMapping("/notes/create")
	@ApiOperation(value = "Api to create a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> create(@RequestBody NoteDto information, @RequestHeader("token") String token) {
		service.createNote(information, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", information));
	}

	/* API for updating a Note */
	@PutMapping("/notes/update")
	@ApiOperation(value = "Api to update a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> update(@RequestBody NoteUpdation note, @RequestHeader("token") String token) {
		service.updateNote(note, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated ", note));
	}

	/* API for pin a Note */
	@PutMapping("/notes/pin/{id}")
	@ApiOperation(value = "Api to pin a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> pin(@PathVariable Long id, @RequestHeader("token") String token) {
		service.pinNote(id, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note pined"));
	}

	/* API for archieve a Note */
	@PutMapping("/notes/archieve/{id}")
	@ApiOperation(value = "Api to archieve a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> archieve(@PathVariable Long id, @RequestHeader("token") String token) {
		service.archieveNote(id, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note archieved"));
	}

	/* API for deleting a note */
	@DeleteMapping("/notes/delete/{id}")
	@ApiOperation(value = "Api to delete a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> delete(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deleteNote(id, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note deleted"));
	}

	/* API for permanently deleting a Note */
	@DeleteMapping("/notes/deletepermanentally/{id}")
	@ApiOperation(value = "Api to delete a note permanentally for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> deletePermanentally(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deleteNotePermanently(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted"));
	}

	/* API for updating color to a Note */
	@PostMapping("/notes/addcolor")
	@ApiOperation(value = "Api to add color to a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addColour(@RequestParam("noteId") Long noteId, @RequestParam("color") String color,
			@RequestHeader("token") String token) {
		service.addColour(noteId, token, color);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note colour changed"));

	}

	/* API for getting all archieve notes Notes */
	@GetMapping("/note/getArchieve/{id}")
	@ApiOperation(value = "Api to get archieved  notes for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getArchieve(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" archieved notes", list));
	}

	/* API for getting all trashed Notes */
	@GetMapping("/note/gettrashed/{id}")
	@ApiOperation(value = "Api to get trashed notes of user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getTrashed(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.gettrashednotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" trashed notes", list));
	}

	/* API for getting all Notes */
	@GetMapping("/note/getallnotes/{id}")
	@ApiOperation(value = "Api to get all notes of a user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getAllNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" All notes", list));
	}

	/* API for getting all Pinned Notes */
	@GetMapping("/note/getPinned/{id}")
	@ApiOperation(value = "Api to get pinned  notes of a user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> getPinned(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getPinneded(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" trashed notes", list));
	}

	/* API for adding remainder to Notes */
	@PostMapping("/note/addremainder/{id}")
	@ApiOperation(value = "Api to add remainder for a  note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addRemainder(@RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId, @RequestBody RemainderDto remainder) {
		service.addRemainder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" remainder added"));
	}

	/* API for removing remainder Notes */
	@GetMapping("/note/removeRemainder/{id}")
	@ApiOperation(value = "Api to remove a remainder for a note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> removeRemainder(@RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId, @RequestBody RemainderDto remainder) {
		service.removeRemainder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" removed remainder"));

	}
}
