package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteService {

	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdation information, String token);

	void deleteNote(Long id, String token);

	void archieveNote(Long id, String token);

	void pinNote(Long id, String token);

	boolean deleteNotePermanently(Long id, String token);

	List<NoteInformation> getAllNotes(String token);

}
