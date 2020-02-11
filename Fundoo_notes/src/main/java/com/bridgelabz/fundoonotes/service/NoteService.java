package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;

public interface NoteService {

	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdation information, String token);

}
