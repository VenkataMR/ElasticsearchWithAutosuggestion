package com.hcl.experian.es.service;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.hcl.experian.es.entity.ContentEntity;
import com.hcl.experian.es.model.Content;
import com.hcl.experian.es.repository.ContentRepository;
import com.hcl.experian.es.transformer.ContentTransformer;

@Service
public class ExperianService {
	
	@Autowired ElasticsearchOperations operations;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	ContentTransformer contentTransformer;

	Logger logger = LoggerFactory.getLogger(ExperianService.class);

	public List<Content> getSuggestions(String requirementStatement) {
		List<Content> content = null;
		
		/*{
		  "query": {
		    "prefix": {
		      "requirementStatement.keywordstring": "th"
		    }
		  }
		}
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
		          .withQuery(prefixQuery("requirementStatement", requirementStatement)).build();*/
		
		//operations.
		
		SuggestBuilder suggestion = new SuggestBuilder();
		
		CompletionSuggestionBuilder csuggestion = new CompletionSuggestionBuilder("requirementStatement.completion");
		csuggestion.prefix(requirementStatement);
		suggestion.addSuggestion("context-suggest", csuggestion);
		
		//We are getting suggestion from elasticsearch we need to get contentEntity from SearchResponse
		SearchResponse searchresponse = elasticsearchTemplate.suggest(suggestion, ContentEntity.class);
		Suggest suggest = searchresponse.getSuggest();
		        
		if (null != suggest) {
			content = contentTransformer.transform(suggest);
		}

		return content;
	}
	
	public List<Content> getSuggestionsWithSpringData(String requirementStatement) {
		List<Content> content = null;
		
		List<ContentEntity> searchResponse = contentRepository.getSuggestions(requirementStatement);
		        
		if (null != searchResponse) {
			content = contentTransformer.transform(searchResponse);
		}

		return content;
	}
	public List<Content> getEdgeNgram(String requirementStatement) {
		List<Content> content = null;
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
		          .withQuery(QueryBuilders.prefixQuery("requirementStatement.edgengram", requirementStatement)).build();
		
		List<ContentEntity> searchResponse = elasticsearchTemplate
				.queryForList(searchQuery, ContentEntity.class);

		if (null != searchResponse) {
			content = contentTransformer.transform(searchResponse);
		}

		return content;
	}
	
	public List<Content> getEdgeNgramWithSpringData(String requirementStatement) {
		List<Content> content = null;

		List<ContentEntity> searchResponse = contentRepository.getEdgeNgram(requirementStatement);
		if (null != searchResponse) {
			content = contentTransformer.transform(searchResponse);
		}

		return content;
	}

	public String addContent(Content content) {
		ContentEntity contentEntity = contentTransformer.transform(content);
		contentRepository.save(contentEntity);
		return "Content Added";
	}

}