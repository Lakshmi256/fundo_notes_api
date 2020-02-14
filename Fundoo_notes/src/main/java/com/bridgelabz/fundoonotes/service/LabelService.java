package com.bridgelabz.fundoonotes.service;
/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface LabelService {

	void createLabel(LabelDto label, String token);

	void createAndMap(LabelDto label, String token, Long id);

	void addLabel(Long labelId, String token, Long noteId);

	void removeLabel(Long labelId, String token, Long noteId);

	void editLabel(LabelUpdate label, String token);

	void deleteLabel(LabelUpdate label, String token);

	List<LabelInformation> getLabel(String token);

	List<NoteInformation> getAllNotes(String token, Long labelId);

}
