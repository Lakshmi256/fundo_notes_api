package com.bridgelabz.fundoonotes.entity;

import lombok.Data;

@Data
public class PasswordUpdate {
	String email;
	String newPassword;
	String confirmPassword;
}
