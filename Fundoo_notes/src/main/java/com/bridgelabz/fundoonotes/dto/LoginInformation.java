package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data

public class LoginInformation {
	@NotNull
	private String email;
	@NotNull
	private String password;

	
}
