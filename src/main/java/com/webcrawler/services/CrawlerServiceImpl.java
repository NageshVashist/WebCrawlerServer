package com.webcrawler.services;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webcrawler.TextSearchRepository;
import com.webcrawler.dao.TextSearchDAO;
import com.webcrawler.dto.ResponseDTO;
import com.webcrawler.exception.WebCrowlerException;
import com.webcrawler.model.TextSearch;
import com.webcrawler.worker.CrawlerExecutor;

@Service
public class CrawlerServiceImpl implements CrawlerService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CrawlerExecutor executor;
	@Autowired
	private TextSearchRepository repo;
	List<TextSearchDAO> list = new LinkedList<>();

	public CrawlerServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional
	public List<ResponseDTO> searchText(List<TextSearch> textSearchList) {
		logger.info("Crawler service called with data :" + textSearchList);
		validateUrls(textSearchList);

		List<ResponseDTO> responseList = new ArrayList<>();
		List<TextSearch> filteredList = textSearchList.stream().filter(t -> {
			List<TextSearchDAO> findAllByUrlAndSearchText = repo.findAllByUrlAndSearchText(t.getUrl(),
					t.getSearchText());
			if (findAllByUrlAndSearchText != null && !findAllByUrlAndSearchText.isEmpty()) {

				list.add(findAllByUrlAndSearchText.get(0));
				return false;
			}
			return true;

		}).collect(Collectors.toList());
//		Crawle urls that are not in db cache and merge in response list
		if (!filteredList.isEmpty()) {
			responseList.addAll(executor.addAll(filteredList));
		}
//		Get records that are in db and merge in response db
		if (!list.isEmpty()) {
			responseList.addAll(
					list.stream().map(t -> new ResponseDTO(t.getUrl(), t.getSearchText(), t.getResult(), t.getStatus()))
							.collect(Collectors.toList()));
		}

		return responseList;

	}

	private void validateUrls(List<TextSearch> textSearchList) {
		for (TextSearch textSearch : textSearchList) {
			try {
				new URL(textSearch.getUrl()).toURI();
			} catch (MalformedURLException | URISyntaxException e) {
				throw new WebCrowlerException("Error:"+e.getMessage());
			}
		}
	}
}
