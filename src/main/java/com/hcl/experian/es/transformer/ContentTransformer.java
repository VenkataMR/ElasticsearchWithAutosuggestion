package com.hcl.experian.es.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion.Entry.Option;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import com.hcl.experian.es.entity.ContentEntity;
import com.hcl.experian.es.model.Content;

@Component
public class ContentTransformer {
	
	public List<Content> transform(Suggest suggestions) {
		List<Content> contentModel = new ArrayList<Content>();
		
		List<List<Option>> optionsOfOptions = suggestions.filter(CompletionSuggestion.class).stream()
				.map(CompletionSuggestion::getOptions).collect(Collectors.toList());

		List<Option> options = optionsOfOptions.stream().flatMap(List::stream).collect(Collectors.toList());
		
		for (Option option : options) {
			Set<String> keys = option.getHit().getSource().keySet();
			Collection<Object> values = option.getHit().getSource().values();
			
			contentModel.add(getContent(keys, values));
		}
		
		return contentModel;
	}

	@SuppressWarnings("unchecked")
	private Content getContent(Set<String> keys, Collection<Object> values) {
		Content content = new Content();
		BeanMap beanMap = BeanMap.create(content);
		
		Map keyValueMap = new LinkedHashMap();
		Object[] valuesArray = values.toArray();
		int i = 0;
		
		for (String key : keys)
			keyValueMap.put(key, valuesArray[i++]);
		
		beanMap.putAll(keyValueMap);
		return content;
	}

	public ContentEntity transform(Content content) {
		ContentEntity contentEntity = new ContentEntity(content.getRequirementStatement(),
				content.getTaxonomyLevel1(), content.getTaxonomyLevel2(), content.getTaxonomyLevel3(),
				content.getTaxonomyLevel4());
		return contentEntity;
	}

	public List<Content> transform(List<ContentEntity> searchResponse) {
		List<Content> contents = new ArrayList<Content>();
		for (ContentEntity contentEntity : searchResponse) {
			contents.add(new Content(contentEntity.getRequirementStatement(), contentEntity.getTaxonomyLevel1(),
					contentEntity.getTaxonomyLevel2(), contentEntity.getTaxonomyLevel3(),
					contentEntity.getTaxonomyLevel4()));
		}
		return contents;
	}

}
