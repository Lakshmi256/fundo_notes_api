package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDto {
	@NotBlank
	private String name;
	@NotNull
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private Long mobileNumber;

}
