package com.webcrawler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webcrawler.dto.RequestDTO;
import com.webcrawler.dto.ResponseDTO;
import com.webcrawler.model.TextSearch;
import com.webcrawler.services.CrawlerService;

@RestController
public class CrawlerController {

	@Autowired
	private CrawlerService service;

	@PostMapping("/search")
	public ResponseEntity<List<ResponseDTO>> search(@RequestBody(required = true) RequestDTO request) {
		List<TextSearch> textSearchList = request.toTextSearch();
		return ResponseEntity.ok(service.searchText(textSearchList));

	}
}
