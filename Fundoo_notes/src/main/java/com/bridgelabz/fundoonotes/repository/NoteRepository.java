package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.dto.NoteInformation;

public interface NoteRepository {

	NoteInformation save(NoteInformation noteInformation);

	NoteInformation findById(Long id);

}
