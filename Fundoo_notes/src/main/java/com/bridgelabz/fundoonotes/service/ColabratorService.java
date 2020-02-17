package com.bridgelabz.fundoonotes.service;

import java.util.List;

/*
 * author:Lakshmi Prasad A
 */
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface ColabratorService {

	NoteInformation addcolab(Long noteId, String email, String token);

	List<NoteInformation> getAllCollabs(String token);

	NoteInformation removeCollab(long noteId, String token, String email);

}
