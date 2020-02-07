package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.LabelDto;

public interface LabelService {

	void createLabel(LabelDto label, String token);

}
