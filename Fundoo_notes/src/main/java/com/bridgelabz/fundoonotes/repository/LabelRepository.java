package com.bridgelabz.fundoonotes.repository;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

public interface LabelRepository {

	LabelInformation save(LabelInformation labelInformation);

	LabelInformation fetchLabel(Long userid, String labelname);

	LabelInformation fetchLabelById(Long id);

	int deleteLabel(Long i);

	List<LabelInformation> getAllLabel(Long id);

	LabelInformation getLabel(Long id);

}
