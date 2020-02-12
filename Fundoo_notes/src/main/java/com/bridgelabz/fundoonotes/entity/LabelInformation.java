package com.bridgelabz.fundoonotes.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "Label_Info")
public class LabelInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long labelId;
	private String name;
	private Long userId;

	@ManyToMany(cascade = CascadeType.ALL)

	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "Label_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })

	@JsonBackReference

	private List<NoteInformation> list;

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
