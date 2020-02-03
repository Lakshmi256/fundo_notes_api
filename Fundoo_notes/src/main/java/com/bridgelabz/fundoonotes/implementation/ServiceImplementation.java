package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
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
	private BCryptPasswordEncoder encryption;
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean register(UserDto information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation = modelMapper.map(information, UserInformation.class);
			userInformation.setCreateDate(LocalDateTime.now());
			String epassword = encryption.encode(information.getPassword());
			userInformation.setPassword(epassword);
			userInformation.setVerified(false);
			userInformation = repository.save(userInformation);
			return true;
		} else
			throw new UserException("user already exists with the same mail id");
	}

	@Transactional
	@Override
	public UserInformation login(LoginInformation information) {
		UserInformation user = repository.getUser(information.getUsername());
		if (user != null) {

			if ((user.isVerified() == true) && (encryption.matches(information.getPassword(), user.getPassword()))) {
				System.out.println(generate.jwtToken(user.getUserId()));
				return user;
			} else {
				// mail
				return null;
			}
		}
		return null;
	}
	@Transactional
	@Override
	public boolean verify(String token) throws Exception {
		System.out.println("id in verification" + (long) generate.parseJWT(token));
		Long id = (long) generate.parseJWT(token);
		repository.verify(id);
		return true;
	}

	@Override
	public boolean isUserExist(String email) {
		try {
			UserInformation user = repository.getUser(email);
			if (user.isVerified() == true) {
				//String mailResponse = response.formMessage("http://localhost:3000/updatePassword",
			//			generate.jwtToken(user.getUserId()));
			//	MailServiceProvider.sendEmail(user.getEmail(), "verification", mailResponse);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new UserException("User doesn't exist");
		}
	}
      

}
