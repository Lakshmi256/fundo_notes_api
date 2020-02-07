package com.bridgelabz.fundoonotes.implementation;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.LabelDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
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

	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {

	}
}
