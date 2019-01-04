package com.hcl.experian.es.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.experian.es.model.Content;
import com.hcl.experian.es.service.ExperianService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/content", produces = "application/json")
@Api(value = "/content")
public class ExperianController {

	Logger logger = LoggerFactory.getLogger(ExperianController.class);

	@Autowired
	private ExperianService experianService;

	@ApiOperation(value = "getSuggestions", notes = "API to get search results with auto-completion feature", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully searched for Results", response = List.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = Exception.class) })
	@RequestMapping(value = "/autosuggestion/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Content>> getSuggestions(
			@ApiParam(value = "Requirement Statement", defaultValue = "captain america the") @RequestParam(name = "requirementStatement") String requirementStatement,
			@ApiParam(value = "Is Spring-data?", defaultValue = "ture") @RequestParam(name = "isSpringData") Boolean isSpringData) {

		List<Content> content = null;
		if (isSpringData)
			content = experianService.getSuggestionsWithSpringData(requirementStatement);
		else
			content = experianService.getSuggestions(requirementStatement);
		return new ResponseEntity<List<Content>>(content, HttpStatus.OK);
	}

	@ApiOperation(value = "getEdgeNgram", notes = "API to get search results with edge-ngram feature", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully searched for Results", response = List.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = Exception.class) })
	@RequestMapping(value = "/ngram/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Content>> getEdgeNgram(
			@ApiParam(value = "Requirement Statement", defaultValue = "am") @RequestParam(name = "requirementStatement") String requirementStatement,
			@ApiParam(value = "Is Spring-data?", defaultValue = "ture") @RequestParam(name = "isSpringData") Boolean isSpringData) {

		List<Content> content = null;
		if (isSpringData)
			content = experianService.getEdgeNgramWithSpringData(requirementStatement);
		else
			content = experianService.getEdgeNgram(requirementStatement);
		
		return new ResponseEntity<List<Content>>(content, HttpStatus.OK);
	}

	@ApiOperation(value = "addContents", notes = "Add content to ES", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added content", response = String.class),
			@ApiResponse(code = 400, message = "Invalid Request", response = Exception.class) })
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addContents(@RequestBody Content content) {
		String response = experianService.addContent(content);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}