package com.bridgelabz.fundoonotes.dto;

/*
 * author:Lakshmi Prasad A
 */
import javax.validation.constraints.NotNull;

public class LabelUpdate {

	@NotNull
	private Long labelId;
	@NotNull
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
}
