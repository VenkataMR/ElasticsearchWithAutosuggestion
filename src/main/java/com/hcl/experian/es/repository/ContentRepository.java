package com.hcl.experian.es.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hcl.experian.es.entity.ContentEntity;

@Repository
public interface ContentRepository extends ElasticsearchRepository<ContentEntity, String> {
	//@Query("{ \"suggest\": { \"content-suggest\" : { \"prefix\" : \"?0\", \"completion\" : { \"field\" : \"requirementStatement.completion\" }}}}")
	//@Query("{ \"content-suggest\" : { \"prefix\" : \"?0\", \"completion\" : { \"field\" : \"requirementStatement.completion\" }}}")
	@Query("{ \"prefix\" : \"?0\", \"completion\" : { \"field\" : \"requirementStatement.completion\" }}")
	List<ContentEntity> getSuggestions(String requirementStatement);
	
	@Query("{\"match\": {\"requirementStatement.edgengram\": \"?0\"}}")
	List<ContentEntity> getEdgeNgram(String requirementStatement);
	
	List<ContentEntity> findByRequirementStatement(String requirementStatement);
	
}