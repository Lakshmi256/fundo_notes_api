package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;

public interface ProfilePic {

	Profile storeObjectInS3(MultipartFile file, String fileName, String contentType, String token);

}
