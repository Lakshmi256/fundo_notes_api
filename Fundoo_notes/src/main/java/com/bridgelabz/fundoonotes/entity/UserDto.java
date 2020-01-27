package com.bridgelabz.fundoonotes.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDto {

	String name;

	String email;

	String password;

	Long mobileNumber;

}
