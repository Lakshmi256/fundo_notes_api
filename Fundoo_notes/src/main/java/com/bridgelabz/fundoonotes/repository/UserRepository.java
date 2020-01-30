package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface UserRepository {
UserInformation save(UserInformation userInfromation);

UserInformation getUser(String email);

UserInformation getUserById(Long id);

boolean upDate(PasswordUpdate information, Long id);

boolean verify(Long id);
}
