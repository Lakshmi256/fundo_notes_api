package com.bridgelabz.fundoonotes.implementation;

/*
 * author:Lakshmi Prasad A
 */
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.RabbitMQSender;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
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

	/* method to register */
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

			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			// MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(),
			// mailObject.getMessage());
			rabbitMQSender.produceMsg(mailObject);
			return true;
		}
		throw new UserException("user already exists with the same mail id");
	}

	/* method for login */
	@Transactional
	@Override
	public UserInformation login(LoginInformation information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user != null) {

			if ((user.isVerified() == true) && (encryption.matches(information.getPassword(), user.getPassword()))) {

				return user;
			} else {
				String mailResposne = response.fromMessage("http://localhost:8080/verify",
						generate.jwtToken(user.getUserId()));
				MailServiceProvider.sendEmail(information.getEmail(), "verification", mailResposne);
				return null;
			}
		} else {
			return null;
		}

	}
	/* method for generating token */

	public String generateToken(Long id) {
		return generate.jwtToken(id);
	}
	/* method for updating password */

	@Transactional
	@Override
	public boolean update(PasswordUpdate information, String token) {
		Long id = null;
		try {
			id = (Long) generate.parseJWT(token);
			String epassword = encryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			return repository.upDate(information, id);

		} catch (Exception e) {
			throw new UserException("invalid credentials");
		}
	}
	/* method for email verification */

	@Transactional
	@Override
	public boolean verify(String token) throws Exception {

		Long id = (long) generate.parseJWT(token);
		repository.verify(id);
		return true;
	}
	/* method for sending mail for forgot password api */

	@Override
	public boolean forgotPassword(String email) {
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
	/* method for getting all users */

	@Transactional
	@Override
	public List<UserInformation> getUsers() {
		List<UserInformation> users = repository.getUsers();
		UserInformation user = users.get(0);
		return users;
	}
	/* method for getting single user */

	@Transactional
	@Override
	public UserInformation getsingleUser(String token) {
		Long id;
		try {
			id = (Long) generate.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = repository.getUserById(id);

		return user;
	}
}
