package com.bridgelabz.fundoonotes.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ColabratorService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class ColabratorServiceImplementation implements ColabratorService {

	@Autowired

	private JwtGenerator token;

	private UserInformation userInformation = new UserInformation();

	@Autowired

	private UserRepository repository;

	@Autowired

	private NoteRepository noteRepository;

}
