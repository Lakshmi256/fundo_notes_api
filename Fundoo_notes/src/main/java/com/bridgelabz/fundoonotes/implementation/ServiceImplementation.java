package com.bridgelabz.fundoonotes.implementation;

import java.io.IOException;
/*
 * author:Lakshmi Prasad A
 */
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.configuration.RabbitMQSender;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.ProfilePicRepository;
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
	private MailResponse response;
	@Autowired
	private MailObject mailObject;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	@Autowired
	private ProfilePicRepository profileRepository;
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserRepository userRepository;
@Autowired
private ModelMapper modelMapper;
	@Value("${bucket}")
	private String bucketName;
	@Autowired
	private AmazonS3 amazonS3Client;

	/* method to register */
	@Transactional
	@Override
	public boolean register(UserDto information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation=modelMapper.map(information, UserInformation.class);
			userInformation.setCreateDate(LocalDateTime.now());
			String epassword = encryption.encode(information.getPassword());
			userInformation.setPassword(epassword);
			userInformation.setVerified(false);
			userInformation = repository.save(userInformation);
			String mailResponse = response.fromMessage("http://localhost:8080/users/verify",
					generate.jwtToken(userInformation.getUserId()));

			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
			// rabbitMQSender.produceMsg(mailObject);
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
				String mailResposne = response.fromMessage("http://localhost:8080/users/verify",
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
				String mailResposne = response.fromMessage("http://localhost:8080/users/verify",
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

	/* method to store object in S3 */
	@Transactional
	@Override
	public Profile storeObjectInS3(MultipartFile file, String fileName, String contentType, String token) {
		try {
			Long userId = (Long) tokenGenerator.parseJWT(token);
			UserInformation user = userRepository.getUserById(userId);
			if (user != null) {

				Profile profile = new Profile(fileName, user);

				ObjectMetadata objectMetadata = new ObjectMetadata();

				objectMetadata.setContentType(contentType);

				objectMetadata.setContentLength(file.getSize());

				amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);

				profileRepository.save(profile);
				return profile;
			}
		} catch (AmazonClientException | IOException exception) {
			throw new RuntimeException("error while uploding file");
		}
		return null;
	}

	/* method to fetch object details */
	@Transactional
	@Override
	public S3Object fetchobject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();
			throw new RuntimeException("error while fetching details");
		}
		return s3Object;
	}

	/* method to delete object */
	@Transactional
	@Override
	public void deleteobject(String key) {
		try {
			amazonS3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();
			throw new RuntimeException("error while deleting  object");
		}
	}

	/* method to update the picture */
	@Transactional
	@Override
	public Profile update(MultipartFile file, String originalFilename, String contentType, String token) {
		try {
			Long userId = tokenGenerator.parseJWT(token);
			UserInformation user = userRepository.getUserById(userId);
			Profile profile = profileRepository.findUserById(userId);
			if (user != null && profile != null) {

				deleteobject(profile.getProfilePicName());
				profileRepository.delete(profile);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());

				amazonS3Client.putObject(bucketName, originalFilename, file.getInputStream(), objectMetadata);
				profileRepository.save(profile);
				return profile;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/* method to get profile picture */
	@Transactional
	@Override
	public S3Object getProfilePic(String token) {
		try {
			Long userId = tokenGenerator.parseJWT(token);
			UserInformation user = userRepository.getUserById(userId);
			if (user != null) {
				Profile profile = profileRepository.findUserById(userId);
				if (profile != null) {
					return fetchobject(profile.getProfilePicName());
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
