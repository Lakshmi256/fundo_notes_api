package com.bridgelabz.fundoonotes.service;
/*
 * author:Lakshmi Prasad A
 */
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface ColabratorService {

	NoteInformation addcolab(Long noteId, String email, String token);



}
