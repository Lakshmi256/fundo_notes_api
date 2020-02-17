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

@RestController
public class LabelController {
	
	@Autowired
	private LabelService service;

	/* API for creating label */
	@PostMapping("/label/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", 200, label));

	}

	/* API for creating and map label */
	@PostMapping("/label/createandmap")
	public ResponseEntity<Response> createandmapLabel(@RequestBody LabelDto label, @RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId) {
		service.createAndMap(label, token, noteId);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", 200, label));

	}

	/* API for add label */
	@PostMapping("/label/addlabel")
	public ResponseEntity<Response> addlabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.addLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added ", 200));

	}

	/* API for removing label */
	@PostMapping("/label/remove")
	public ResponseEntity<Response> removelabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.removeLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label removed ", 200));

	}

	/* API for updating label */
	@PutMapping("/label/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {
		service.editLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label updated ", 200, label));

	}

	/* API for deleting label */
	@PostMapping("/label/delete")
	public ResponseEntity<Response> deleteLabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {
		service.deleteLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted ", 200, label));

	}

	/* API for getting all label */
	@GetMapping("/label/getlabels")
	public ResponseEntity<Response> getLabels(@RequestHeader("token") String token) {
		List<LabelInformation> labels = service.getLabel(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("getting all labels  ", 200, labels));

	}

	/* API for getting all labelNotes */
	@GetMapping("/label/getlabelsnotes")
	public ResponseEntity<Response> getNotes(@RequestHeader("token") String token,
			@RequestParam("labelId") Long labelId) {
		List<NoteInformation> list = service.getAllNotes(token, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("getting labelnotes  ", 200, list));

	}
}
