package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PasswordUpdate {
	@NotNull
	private String email;
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;

}
