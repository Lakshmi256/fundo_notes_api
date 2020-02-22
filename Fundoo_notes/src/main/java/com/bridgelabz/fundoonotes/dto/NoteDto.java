package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class NoteDto {
	@NotNull
	private String title;
	@NotNull
	private String description;

	
}
