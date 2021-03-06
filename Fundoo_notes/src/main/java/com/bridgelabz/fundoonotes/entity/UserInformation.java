package com.bridgelabz.fundoonotes.entity;

/*
 * author:Lakshmi Prasad A
 */
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "usersdetail")
public class UserInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	private String name;

	private String email;

	private String password;

	private Long mobileNumber;

	private boolean isVerified;

	private LocalDateTime createDate;

	@OneToMany(cascade = CascadeType.ALL)

	@JoinColumn(name = "userId")

	private List<NoteInformation> note;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "collaborator_note", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	@JsonIgnore
	private List<NoteInformation> colabrateNote;

}