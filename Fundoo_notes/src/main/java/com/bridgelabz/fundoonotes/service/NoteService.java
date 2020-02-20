package com.bridgelabz.fundoonotes.service;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteService {

	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdation information, String token);

	void deleteNote(Long id, String token);

	void archieveNote(Long id, String token);

	void pinNote(Long id, String token);

	boolean deleteNotePermanently(Long id, String token);

	List<NoteInformation> getAllNotes(String token);

	List<NoteInformation> gettrashednotes(String token);

	List<NoteInformation> getarchieved(String token);

	void addColour(Long noteId, String token, String colour);

	List<NoteInformation> getPinneded(String token);

	void addRemainder(Long noteid, String token, RemainderDto remainder);

	void removeRemainder(Long noteid, String token, RemainderDto remainder);

}
