package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.RabbitMQSender;
import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

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
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;
	@Autowired
	private RabbitMQSender rabbitMQSender;

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
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.jwtToken(userInformation.getUserId()));
			System.out.println(mailResponse);
			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			rabbitMQSender.send(mailObject);
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
				String mailResposne = response.fromMessage("http://localhost:8080/verify",
						generate.jwtToken(user.getUserId()));
				MailServiceProvider.sendEmail(information.getUsername(), "verification", mailResposne);
				return null;
			}
		} else {
			return null;
		}

	}

	public String generateToken(Long id) {
		return generate.jwtToken(id);
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
				String mailResposne = response.fromMessage("http://localhost:8080/verify",
						generate.jwtToken(user.getUserId()));
				MailServiceProvider.sendEmail(user.getEmail(), "verification", mailResposne);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new UserException("User doesn't exist");
		}
	}

}
