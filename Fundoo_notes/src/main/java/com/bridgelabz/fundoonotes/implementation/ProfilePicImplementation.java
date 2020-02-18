package com.bridgelabz.fundoonotes.implementation;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.ProfilePicRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfilePic;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class ProfilePicImplementation implements ProfilePic {
	@Autowired
	private ProfilePicRepository profileRepository;
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserRepository userRepository;
	private String bucketName = System.getenv("bucketname");
	@Autowired
	private AmazonS3 amazons3Client;

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

				amazons3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
				profileRepository.save(profile);
				return profile;
			}
		} catch (AmazonClientException | IOException exception) {
			throw new RuntimeException("error while uploding file");
		}
		return null;
	}

	@Transactional
	@Override
	public S3Object fetchobject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazons3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();
			throw new RuntimeException("error while fetching details");
		}
		return s3Object;
	}

	@Transactional
	@Override
	public void deleteobject(String key) {
		try {
			amazons3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();
			throw new RuntimeException("error while deleting  object");
		}
	}
}
