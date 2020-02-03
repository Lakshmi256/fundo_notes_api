package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface Services {

	boolean register(UserDto information);

	UserInformation login(LoginInformation information);

	boolean verify(String token) throws Exception;

	boolean isUserExist(String email);


}
