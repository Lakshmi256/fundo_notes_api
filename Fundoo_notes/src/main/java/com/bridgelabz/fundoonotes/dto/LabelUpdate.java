package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class LabelUpdate {

	@NotNull
	private Long labelId;
	@NotNull
	private String labelName;

	
}
