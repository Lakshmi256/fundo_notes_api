package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordUpdate {
	@NotNull
	private String email;
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
