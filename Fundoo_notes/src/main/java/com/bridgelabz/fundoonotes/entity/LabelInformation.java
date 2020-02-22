package com.bridgelabz.fundoonotes.entity;

/*
 * author:Lakshmi Prasad A
 */
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

import lombok.Data;

@Data
@Entity
@Table(name = "Label_Info")
public class LabelInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;
	private String name;
	private Long userId;

	@ManyToMany(cascade = CascadeType.ALL)

	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "label_id") }, inverseJoinColumns = {
			@JoinColumn(name = "id") })

	private List<NoteInformation> list;

}
