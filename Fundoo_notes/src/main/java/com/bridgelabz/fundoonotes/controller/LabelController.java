package com.bridgelabz.fundoonotes.controller;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

import io.swagger.annotations.ApiOperation;

@RestController
public class LabelController {

	@Autowired
	private LabelService service;

	/* API for creating label */
	@PostMapping("/labels/create")
	@ApiOperation(value = "Api to create a label for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", 200, label));

	}

	/* API for creating and map label */
	@PostMapping("/labels/createandmap")
	@ApiOperation(value = "Api to create a label and map with note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> createandmapLabel(@RequestBody LabelDto label, @RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId) {
		service.createAndMap(label, token, noteId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", 200, label));

	}

	/* API for mapping label with note */
	@PostMapping("/labels/addlabel")
	@ApiOperation(value = "Api to map a label with note for user in Fundoonotes", response = Response.class)
	public ResponseEntity<Response> addlabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.addLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added ", 200));

	}

	/* API for removing label and note maping */
	@PostMapping("/labels/remove")
	@ApiOperation(value = "Api to remove mapping for label and note for user in Fundoonotes", response = Response.class)

	public ResponseEntity<Response> removelabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.removeLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label removed ", 200));

	}

	/* API for updating label */
	@PutMapping("/labels/update")
	@ApiOperation(value = "Api to update a label  for user in Fundoonotes", response = Response.class)

	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {
		service.editLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label updated ", 200, label));

	}

	/* API for deleting label */
	@PostMapping("/labels/delete")
	@ApiOperation(value = "Api to delete a label for user in Fundoonotes", response = Response.class)

	public ResponseEntity<Response> deleteLabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {
		service.deleteLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted ", 200, label));

	}

	/* API for getting all label */
	@GetMapping("/labels/getlabels")
	@ApiOperation(value = "Api to get all labels for user in Fundoonotes", response = Response.class)

	public ResponseEntity<Response> getLabels(@RequestHeader("token") String token) {
		List<LabelInformation> labels = service.getLabel(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("getting all labels  ", 200, labels));

	}

	/* API for getting all labelNotes */
	@GetMapping("/labels/getlabelsnotes")
	@ApiOperation(value = "Api to get all notes of a label for user in Fundoonotes", response = Response.class)

	public ResponseEntity<Response> getNotes(@RequestHeader("token") String token,
			@RequestParam("labelId") Long labelId) {
		List<NoteInformation> list = service.getAllNotes(token, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("getting labelnotes  ", 200, list));

	}
}
