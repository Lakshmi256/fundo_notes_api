package com.bridgelabz.fundoonotes.dto;
/*
 * author:Lakshmi Prasad A
 */
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RemainderDto {

	private LocalDateTime remainder;

	public LocalDateTime getRemainder() {
		return remainder;
	}

	public void setRemainder(LocalDateTime remainder) {
		this.remainder = remainder;
	}
}
