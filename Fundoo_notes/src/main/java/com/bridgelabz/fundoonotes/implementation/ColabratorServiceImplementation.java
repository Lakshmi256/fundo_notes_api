package com.bridgelabz.fundoonotes.implementation;

import java.util.List;

/*
 * author:Lakshmi Prasad A
 */
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ColabratorService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class ColabratorServiceImplementation implements ColabratorService {

	@Autowired
	private JwtGenerator tokenGenerator;

	private UserInformation userInformation = new UserInformation();

	@Autowired

	private UserRepository repository;

	@Autowired

	private NoteRepository noteRepository;

	/* method to add the colaborator */
	@Transactional
	@Override
	public NoteInformation addcolab(Long noteId, String email, String token) {
		UserInformation user;
		UserInformation collabrator = repository.getUser(email);
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
		} catch (Exception e) {
			throw new UserException("user not present");
		}
		if (user != null) {
			if (collabrator != null) {
				NoteInformation note = noteRepository.findById(noteId);
				collabrator.getColabrateNote().add(note);
				return note;
			} else {
				throw new UserException("user not present to collab");
			}
		} else {
			throw new UserException("user does not exits");
		}

	}

	/* method to get all colabrators */
	@Transactional
	@Override
	public List<NoteInformation> getAllCollabs(String token) {
		Long userid = tokenGenerator.parseJWT(token);
		UserInformation user = repository.getUserById(userid);
		List<NoteInformation> note = user.getColabrateNote();
		return note;
	}

	/* method to remove the colaborator */
	@Transactional
	@Override
	public NoteInformation removeCollab(long noteId, String token, String email) {
		UserInformation user;
		UserInformation collabrator = repository.getUser(email);
		try {
			Long userid = tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
		} catch (Exception e) {
			throw new UserException("user not found");
		}
		NoteInformation note = noteRepository.findById(noteId);
		note.getColabUser().remove(collabrator);
		return null;
	}

}
