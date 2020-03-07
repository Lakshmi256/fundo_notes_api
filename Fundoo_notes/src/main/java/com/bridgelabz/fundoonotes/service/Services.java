package com.bridgelabz.fundoonotes.service;

/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface Services {

	boolean register(UserDto information);

	UserInformation login(LoginInformation information);

	boolean verify(String token) throws Exception;

	boolean update(PasswordUpdate information, String token);

	List<UserInformation> getUsers();

	UserInformation getsingleUser(String token);

	Long forgotPassword(String email);

	Profile storeObjectInS3(MultipartFile file, String fileName, String contentType, String token);

	S3Object fetchobject(String awsFileName);

	void deleteobject(String key);

	Profile update(MultipartFile file, String originalFilename, String contentType, String token);

	S3Object getProfilePic(String token);

}
