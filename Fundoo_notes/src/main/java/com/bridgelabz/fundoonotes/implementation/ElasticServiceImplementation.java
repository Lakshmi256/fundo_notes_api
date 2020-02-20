package com.bridgelabz.fundoonotes.implementation;

/*
 * author:Lakshmi Prasad A
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.service.ElasticService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticServiceImplementation implements ElasticService {

	@Autowired
	private ElasticSearchConfig elasticSearch;

	@Autowired
	private ObjectMapper objectMapper;

	private String INDEX = "springboot";

	private String TYPE = "note_info";

	@Override
	public String createNote(NoteInformation information) {
		Map<String, Object> notemapper = objectMapper.convertValue(information, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(information.getId()))
				.source(notemapper);
		IndexResponse indexResposnse = null;
		try {
			indexResposnse = elasticSearch.client().index(indexRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexResposnse.getResult().name();
	}

	@Override
	public String updateNote(NoteInformation note) {
		Map<String, Object> notemapper = objectMapper.convertValue(note, Map.class);
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getId())).doc(notemapper);
		UpdateResponse updateResponse = null;
		try {
			updateResponse = elasticSearch.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateResponse.getResult().name();
	}

	@Override
	public String DeleteNote(NoteInformation note) {
		Map<String, Object> notemapper = objectMapper.convertValue(note, Map.class);
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getId()));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = elasticSearch.client().delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}

	@Override
	public List<NoteInformation> searchByTitle(String title) {
		SearchRequest searchRequest = new SearchRequest("springboot");
		SearchSourceBuilder searchSource = new SearchSourceBuilder();
		searchSource.query(QueryBuilders.matchQuery("title", title));
		searchRequest.source(searchSource);
		SearchResponse searchResponse = null;
		try {
			searchResponse = elasticSearch.client().search(searchRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getResult(searchResponse);
	}

	@Override
	public List<NoteInformation> getResult(SearchResponse searchResponse) {
		SearchHit[] searchhits = searchResponse.getHits().getHits();
		List<NoteInformation> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteInformation.class)));
		}
		return notes;
	}

}
