package com.bridgelabz.fundoonotes.implementation;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class LabelServiceImplementation implements LabelService {

	private UserInformation user = new UserInformation();
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private LabelRepository labelRepository;

	private LabelInformation labelInformation;

	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				labelInformation = modelMapper.map(label, LabelInformation.class);
				labelInformation.getLabelId();
				labelInformation.getName();
				labelInformation.setUserId(user.getUserId());
				labelRepository.save(labelInformation);
			} else {
				throw new UserException("label with that name is already present ");
			}
		} else {
			throw new UserException("note does not exist with userid");
		}
	}

}
