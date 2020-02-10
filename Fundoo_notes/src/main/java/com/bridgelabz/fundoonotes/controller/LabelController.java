package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
public class LabelController {
	@Autowired
	private LabelService service;
	/*API for creating label*/
	@PostMapping("/label/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", 200, label));
	}
}
