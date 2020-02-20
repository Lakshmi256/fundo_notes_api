package com.bridgelabz.fundoonotes.implementation;

/*
 * author:Lakshmi Prasad A
 */
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
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
				note.setUpDateAndTime(LocalDateTime.now());
				noteRepository.save(note);

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
				note.setPinned(false);
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
				note.setArchieved(false);
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
			throw new UserException("user does not exist");
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> gettrashednotes(String token) {
		try {
			Long userId = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getTrashedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getarchieved(String token) {
		try {
			Long userId = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getArchievedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public void addColour(Long noteId, String token, String colour) {
		try {
			Long userId = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				NoteInformation note = noteRepository.findById(noteId);
				if (note != null) {
					note.setColour(colour);
					noteRepository.save(note);
				} else {
					throw new UserException("note does not exist");
				}
			} else {
				throw new UserException("user does not exists");
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getPinneded(String token) {
		try {
			Long userId = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getPinnededNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public void addRemainder(Long noteid, String token, RemainderDto remainder) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(noteid);
			if (note != null) {
				System.out.println(remainder.getRemainder());
				note.setReminder(remainder.getRemainder());
				noteRepository.save(note);

			} else {
				throw new UserException("note does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public void removeRemainder(Long noteid, String token, RemainderDto remainder) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation note = noteRepository.findById(noteid);
			if (note != null) {
				note.setReminder(null);
				noteRepository.save(note);

			} else {
				throw new UserException("note does not exist");
			}

		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

}
