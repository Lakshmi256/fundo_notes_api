package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class LabelInformation {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int labelId;
private String name;
private Long userId;
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
