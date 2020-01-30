package com.bridgelabz.fundoonotes.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.LoginInformation;
import com.bridgelabz.fundoonotes.entity.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.service.Services;
@Service
public class ServiceImplementation implements Services{
	private UserInformation userInformation=new UserInformation();
	@Autowired
	private IUser
	@Override
	public boolean register(UserDto information) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserInformation login(LoginInformation information) {
		// TODO Auto-generated method stub
		return null;
	}

}
