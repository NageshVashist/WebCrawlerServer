package com.webcrawler.services;

import java.util.List;

import com.webcrawler.dto.ResponseDTO;
import com.webcrawler.model.TextSearch;

public interface CrawlerService {

	public List<ResponseDTO> searchText(List<TextSearch> textSearchList);

}
