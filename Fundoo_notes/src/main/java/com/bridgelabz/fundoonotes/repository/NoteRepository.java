package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteRepository {

	NoteInformation save(NoteInformation noteInformation);

	NoteInformation findById(Long id);

	boolean deleteNote(Long id, Long userid);

	List<NoteInformation> getNotes(Long userid);

	List<NoteInformation> getTrashedNotes(Long userid);

	List<NoteInformation> getArchievedNotes(Long userid);

	boolean updateColor(Long id, Long userid, String color);

	List<NoteInformation> getPinnededNotes(Long userid);

}
