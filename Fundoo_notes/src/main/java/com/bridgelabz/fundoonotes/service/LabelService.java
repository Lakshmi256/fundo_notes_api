package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;

public interface LabelService {

	void createLabel(LabelDto label, String token);

	void createAndMap(LabelDto label, String token, Long id);

	void addLabel(Long labelId, String token, Long noteId);

	void removeLabel(Long labelId, String token, Long noteId);

	void editLabel(LabelUpdate label, String token);

	void deleteLabel(LabelUpdate label, String token);

}
