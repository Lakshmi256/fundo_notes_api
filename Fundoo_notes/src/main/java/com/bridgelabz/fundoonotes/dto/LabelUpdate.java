package com.bridgelabz.fundoonotes.dto;

public class LabelUpdate {
	private Long labelId;
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
