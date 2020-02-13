package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class NoteServiceImplementation implements NoteService {
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserRepository repository;
	private UserInformation user = new UserInformation();
	@Autowired
	private NoteRepository noteRepository;
	private NoteInformation noteInformation = new NoteInformation();
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			if (user != null) {
				noteInformation = modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				noteInformation.setColour("white");
				user.getNote().add(noteInformation);
				NoteInformation note = noteRepository.save(noteInformation);
				if (note != null) {
					final String key = user.getEmail();
				}
			}
		} catch (Exception e) {
			throw new UserException("user is not present with given id");
		}
	}

	@Transactional
	@Override
	public void updateNote(NoteUpdation information, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);

			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(information.getId());
			if (note != null) {
				note.setId(information.getId());
				note.setDescription(information.getDescription());
				note.setTitle(information.getTitle());
				note.setPinned(information.isPinned());
				note.setTrashed(information.isTrashed());
				note.setArchieved(information.isArchieved());
				note.setUpDateAndTime(information.getUpDateAndTime());
				NoteInformation note1 = noteRepository.save(note);

			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public void deleteNote(Long id, String token) {
		try {

			Long userid = (Long) tokenGenerator.parseJWT(token);

			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(id);
			if (note != null) {
				note.setTrashed(!note.isTrashed());
				noteRepository.save(note);
			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public void archieveNote(Long id, String token) {
		try {

			Long userid = (Long) tokenGenerator.parseJWT(token);

			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(id);
			if (note != null) {
				note.setArchieved(!note.isArchieved());
				noteRepository.save(note);
			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public void pinNote(Long id, String token) {
		try {

			Long userid = (Long) tokenGenerator.parseJWT(token);

			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(id);
			if (note != null) {
				note.setPinned(!note.isPinned());
				noteRepository.save(note);
			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public boolean deleteNotePermanently(Long id, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(id);
			if (note != null) {
				List<LabelInformation> labels = note.getList();
				if (labels != null) {
					labels.clear();

				}
				noteRepository.deleteNote(id, userid);

			} else {
				throw new UserException("note is not present");
			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
		return false;
	}

	@Transactional
	@Override
	public List<NoteInformation> getAllNotes(String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getNotes(userid);
				return list;
			}
			throw new UserException("note does not exist");
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}
}
