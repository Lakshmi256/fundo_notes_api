package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface ElasticService {

	String createNote(NoteInformation information);

	String updateNote(NoteInformation note);

	String DeleteNote(NoteInformation note);

	List<NoteInformation> searchByTitle(String title);

	List<NoteInformation> getResult(SearchResponse searchResponse);

}
