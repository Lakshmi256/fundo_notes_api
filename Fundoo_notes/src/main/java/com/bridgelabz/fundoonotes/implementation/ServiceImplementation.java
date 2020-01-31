package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class ServiceImplementation implements Services {
	private UserInformation userInformation = new UserInformation();

	@Autowired
	private UserRepository repository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean register(UserDto information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation = modelMapper.map(information, UserInformation.class);
			userInformation.setCreateDate(LocalDateTime.now());
		}
		return false;
	}

	@Override
	public UserInformation login(LoginInformation information) {
		// TODO Auto-generated method stub
		return null;
	}

}
