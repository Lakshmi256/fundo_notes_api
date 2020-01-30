package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface UserRepository {
UserInformation save(UserInformation userInfromation);

UserInformation getUser(String email);
}
