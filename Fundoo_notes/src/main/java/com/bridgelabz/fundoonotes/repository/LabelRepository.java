package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

public interface LabelRepository {

	LabelInformation save(LabelInformation labelInformation);

	LabelInformation fetchLabel(Long userid, String labelname);

	LabelInformation fetchLabelById(Long id);

}
